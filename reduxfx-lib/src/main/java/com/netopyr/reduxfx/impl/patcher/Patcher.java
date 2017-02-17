package com.netopyr.reduxfx.impl.patcher;

import com.netopyr.reduxfx.impl.differ.patches.AppendPatch;
import com.netopyr.reduxfx.impl.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.impl.differ.patches.Patch;
import com.netopyr.reduxfx.impl.differ.patches.RemovePatch;
import com.netopyr.reduxfx.impl.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.impl.differ.patches.UpdateRootPatch;
import com.netopyr.reduxfx.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.Tuple;
import javaslang.collection.Seq;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static com.netopyr.reduxfx.impl.patcher.NodeUtilities.appendNode;
import static com.netopyr.reduxfx.impl.patcher.NodeUtilities.getChild;
import static com.netopyr.reduxfx.impl.patcher.NodeUtilities.removeNode;
import static com.netopyr.reduxfx.impl.patcher.NodeUtilities.replaceNode;
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

        for (final Patch patch : patches) {

            LOG.trace("\n\nPatch:\n{}", patch);

            final Object node;
            try {
                node = vRoot.isDefined() ? findNode(patch.getPath(), root, vRoot.get()) : root;
            } catch (RuntimeException ex) {
                LOG.error("Unable to apply patch", ex);
                continue;
            }

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
        LOG.error("Unable to replace node {} with {}", oldNode, vNode);
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
            LOG.error("Unable to remove node {}", node);
        }
    }

    private void doUpdateRoot(Object node, UpdateRootPatch patch) {
        final VNode vNode = patch.getNewNode();
        nodeBuilder.updateProperties(node, vNode.getProperties());
        nodeBuilder.updateEventHandlers(node, vNode.getEventHandlers()
                .map((key, value) -> Tuple.of(key, Option.of(value))));
        nodeBuilder.updateProperties(node, vNode.getNamedChildren());


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

    private Object findNode(Vector<Object> path, Object node, VNode vNode) {

        while (! path.isEmpty()) {
            final Object head = path.head();
            path = path.tail();

            if (head instanceof Integer) {
                final int index = (Integer) head;
                node = getChild(node, index);
                vNode = vNode.getChildren().get(index);

            } else if (head instanceof String) {
                final String name = (String) head;
                node = getChild(node, name);
                vNode = (VNode) vNode.getNamedChildren().get(name).get().getValue();

            }
        }

        return node;
    }


    private static Patcher INSTANCE;

    public static Patcher getInstance() {
        return INSTANCE;
    }
}
