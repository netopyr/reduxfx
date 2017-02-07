package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.differ.patches.AppendPatch;
import com.netopyr.reduxfx.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.differ.patches.RemovePatch;
import com.netopyr.reduxfx.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.differ.patches.UpdateRootPatch;
import com.netopyr.reduxfx.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Parent;
import javaslang.Tuple;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static com.netopyr.reduxfx.patcher.NodeUtilities.appendNode;
import static com.netopyr.reduxfx.patcher.NodeUtilities.getChild;
import static com.netopyr.reduxfx.patcher.NodeUtilities.getParent;
import static com.netopyr.reduxfx.patcher.NodeUtilities.removeNode;
import static com.netopyr.reduxfx.patcher.NodeUtilities.replaceNode;
import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.API.run;
import static javaslang.Predicates.instanceOf;

public class Patcher {

    private static final Logger LOG = LoggerFactory.getLogger(Patcher.class);

    private final NodeBuilder nodeBuilder;

    public Patcher(Consumer<Object> dispatcher) {
        final Accessors accessors = new Accessors(dispatcher);
        this.nodeBuilder = new NodeBuilder(dispatcher, accessors);
        accessors.init(nodeBuilder);

        INSTANCE = this;
    }


    public void patch(Object root, Option<VNode> vRoot, Seq<Patch> patches) {

        LOG.trace("Patches:\n{}", patches);

        for (final Patch patch : patches) {
            final Object node = vRoot.flatMap(vNode -> findNode(patch.getIndex(), root, vNode, 0)).getOrElse(root);

            Match(patch).of(

                    Case(instanceOf(ReplacePatch.class),
                            replacePatch -> run(() ->
                                    doReplace(node, replacePatch)
                            )
                    ),

                    Case(instanceOf(AttributesPatch.class),
                            attributesPatch -> run(() ->
                                    doAttributes(node, attributesPatch)
                            )
                    ),

//                    Case(instanceOf(OrderPatch.class),
//                            replacePatch -> run(() -> doReplace(node, replacePatch))
//                    ),

                    Case(instanceOf(AppendPatch.class),
                            appendPatch -> run(() ->
                                    doAppend(node, appendPatch)
                            )
                    ),

                    Case(instanceOf(RemovePatch.class),
                            removePatch -> run(() ->
                                    doRemove(node)
                            )
                    ),

                    Case(instanceOf(UpdateRootPatch.class),
                            updateRootPatch -> run(() ->
                                    doUpdateRoot(node, updateRootPatch)
                            )
                    ),

                    Case($(), o -> run(() -> {
                        throw new IllegalArgumentException("Unknown patch received " + patch);
                    }))
            );
        }

    }

    @SuppressWarnings("unchecked")
    private void doReplace(Object oldNode, ReplacePatch patch) {
        final VNode vNode = patch.getNewNode();
        final Option<Object> newNode = nodeBuilder.create(vNode);
        if (newNode.isDefined()) {
            if (replaceNode(oldNode, newNode.get())) {
                nodeBuilder.init(newNode.get(), vNode);
                return;
            }
        }
        LOG.error("Unable to replace node from parent class {}", getParent(oldNode).getClass());
    }

    @SuppressWarnings("unchecked")
    private void doAttributes(Object node, AttributesPatch patch) {
        nodeBuilder.updateProperties(node, patch.getProperties());
        nodeBuilder.updateEventHandlers(node, patch.getEventHandlers());
    }

    @SuppressWarnings("unchecked")
    private void doAppend(Object parent, AppendPatch patch) {
        final VNode vNode = patch.getNewNode();
        final Option<Object> node = nodeBuilder.create(vNode);
        if (node.isDefined()) {
            if (!appendNode(parent, node.get())) {
                LOG.error("Unable to append node '{}' to parent '{}'", node.get(), parent);
                return;
            }
            nodeBuilder.init(node.get(), vNode);
            return;
        }
        LOG.error("Unable to add node to parent class {}", parent.getClass());
    }

    private static void doRemove(Object node) {
        if (!removeNode(node)) {
            LOG.error("Unable to remove node from parent class {}", getParent(node).getClass());
        }
    }

    private void doUpdateRoot(Object node, UpdateRootPatch patch) {
        final VNode vNode = patch.getNewNode();
        nodeBuilder.updateProperties(node, vNode.getProperties());
        nodeBuilder.updateEventHandlers(node, vNode.getEventHandlers()
                .map((key, value) -> Tuple.of(key, Option.of(value))));


        for (final VNode vChild : vNode.getChildren()) {
            final Option<Object> child = nodeBuilder.create(vChild);
            if (child.isDefined()) {
                if (!appendNode(node, child.get())) {
                    LOG.error("Unable to append node '{}' to parent '{}'", child.get(), node);
                    continue;
                }
                nodeBuilder.init(child.get(), vChild);
            }
        }
    }

    private static Option<Object> findNode(int needle, Object node, VNode vNode, int index) {
        if (needle == index) {
            return Option.of(node);
        }
        if (!(node instanceof Parent)) {
            LOG.error("Unable to navigate to children of class {}", node.getClass());
            return Option.none();
        }

        final Seq<Integer> sizes = vNode.getChildren()
                .map(VNode::getSize)
                .scan(index + 1, (a, b) -> a + b)
                .takeWhile(value -> value <= needle);
        final int child = sizes.length() - 1;

        return findNode(needle, getChild(node, child).get(), vNode.getChildren().get(child), sizes.last());
    }


    private static Patcher INSTANCE;

    public static Patcher getInstance() {
        return INSTANCE;
    }
}
