package com.netopyr.reduxfx.vscenegraph.impl.patcher;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.AppendPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.RemovePatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.SetChildrenPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.SetSingleChildPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.UpdateRootPatch;
import com.netopyr.reduxfx.vscenegraph.property.VProperty.Phase;
import io.vavr.API;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

import static com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities.getProperty;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.Predicates.instanceOf;

public class Patcher {

    private static final Logger LOG = LoggerFactory.getLogger(Patcher.class);

    private Patcher() {
    }

    public static void patch(Consumer<Object> dispatcher, Object root, Option<VNode> vRoot, Map<Phase, Vector<Patch>> patches) {

        for (final Phase phase : Phase.values()) {
            for (final Patch patch : patches.get(phase).getOrElse(Vector.empty())) {

                LOG.trace("\n\nPatch:\n{}", patch);

                final Object node;
                try {
                    node = vRoot.isDefined() ? findNode(patch.getPath(), root) : root;
                } catch (RuntimeException ex) {
                    LOG.error("Unable to apply patch", ex);
                    continue;
                }

                //noinspection RedundantTypeArguments
                Match(patch).of(

                        Case($(instanceOf(AppendPatch.class)),
                                appendPatch -> run(() ->
                                        doAppend(dispatcher, node, appendPatch)
                                )
                        ),

                        Case($(instanceOf(ReplacePatch.class)),
                                replacePatch -> run(() ->
                                        doReplace(dispatcher, node, replacePatch)
                                )
                        ),

                        Case($(instanceOf(RemovePatch.class)),
                                removePatch -> run(() ->
                                        doRemove(node, removePatch)
                                )
                        ),

                        Case($(instanceOf(AttributesPatch.class)),
                                attributesPatch -> run(() ->
                                        doAttributes(dispatcher, node, attributesPatch)
                                )
                        ),

//                    Case($(instanceOf(OrderPatch.class)),
//                            replacePatch -> run(() -> doReplace(node, replacePatch))
//                    ),

                        Case($(instanceOf(SetSingleChildPatch.class)),
                                setSingleChildPatch -> run(() ->
                                        doSetSingleChild(dispatcher, node, setSingleChildPatch)
                                )
                        ),

                        Case($(instanceOf(SetChildrenPatch.class)),
                                setChildrenPatch -> run(() ->
                                        doSetChildren(dispatcher, node, setChildrenPatch)
                                )
                        ),

                        Case($(instanceOf(UpdateRootPatch.class)),
                                updateRootPatch -> run(() ->
                                        doUpdateRoot(dispatcher, node, updateRootPatch)
                                )
                        ),

                        Case(API.<Patch>$(),
                                o -> run(() -> {
                                    throw new IllegalArgumentException("Unknown patch received " + patch);
                                })
                        )
                );
            }
        }

    }

    @SuppressWarnings("unchecked")
    private static void doAppend(Consumer<Object> dispatcher, Object parent, AppendPatch patch) {
        if (!(parent instanceof List)) {
            LOG.error("Unable to add node to parent class {}", parent.getClass());
            return;
        }

        final VNode vNode = patch.getNewNode();
        final Option<Object> newNode = NodeBuilder.create(vNode);
        if (newNode.isEmpty()) {
            LOG.error("Unable to create node " + patch.getNewNode());
            return;
        }

        ((List) parent).add(newNode.get());
        NodeBuilder.init(dispatcher, newNode.get(), vNode);
    }

    @SuppressWarnings("unchecked")
    private static void doReplace(Consumer<Object> dispatcher, Object parent, ReplacePatch patch) {
        if (!(parent instanceof List)) {
            LOG.error("Unable to replace node from parent class {}", parent.getClass());
            return;
        }

        final VNode vNode = patch.getNewNode();
        final Option<Object> newNode = NodeBuilder.create(vNode);
        if (newNode.isEmpty()) {
            LOG.error("Unable to create node " + patch.getNewNode());
            return;
        }

        ((List) parent).set(patch.getIndex(), newNode.get());
        NodeBuilder.init(dispatcher, newNode.get(), vNode);
    }

    private static void doRemove(Object parent, RemovePatch patch) {
        if (!(parent instanceof List)) {
            LOG.error("Unable to add node to parent class {}", parent.getClass());
            return;
        }

        ((List) parent).subList(patch.getStartRange(), patch.getEndRange()).clear();
    }

    @SuppressWarnings("unchecked")
    private static void doAttributes(Consumer<Object> dispatcher, Object node, AttributesPatch patch) {
        NodeBuilder.updateProperties(dispatcher, node, patch.getProperties());
        NodeBuilder.updateEventHandlers(dispatcher, node, patch.getEventHandlers());
    }

    private static void doSetSingleChild(Consumer<Object> dispatcher, Object parent, SetSingleChildPatch patch) {
        NodeBuilder.setSingleChild(dispatcher, parent, patch.getName(), patch.getChild());
    }

    private static void doSetChildren(Consumer<Object> dispatcher, Object parent, SetChildrenPatch patch) {
        NodeBuilder.setChildren(dispatcher, parent, patch.getName(), patch.getChildren());
    }

    private static void doUpdateRoot(Consumer<Object> dispatcher, Object rootNode, UpdateRootPatch patch) {
        NodeBuilder.init(dispatcher, rootNode, patch.getRootNode());
    }

    private static Object findNode(Vector<Object> fullPath, Object root) {

        Vector<Object> path = fullPath;
        Object node = root;

        while (!path.isEmpty()) {

            final Object head = path.head();
            path = path.tail();

            if (head instanceof Integer) {
                final int index = (Integer) head;
                if (node instanceof List) {
                    node = ((List) node).get(index);
                } else {

                    throw new IllegalStateException("Tried to get " + index + ". element from " + node);
                }

            } else if (head instanceof String) {
                final String name = (String) head;
                node = getProperty(node, name);

            }
        }

        return node;
    }
}
