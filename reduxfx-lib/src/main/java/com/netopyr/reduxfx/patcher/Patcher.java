package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.patcher.patches.AttributesPatch;
import com.netopyr.reduxfx.patcher.patches.InsertPatch;
import com.netopyr.reduxfx.patcher.patches.Patch;
import com.netopyr.reduxfx.patcher.patches.ReplacePatch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Node;
import javafx.scene.Parent;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static com.netopyr.reduxfx.patcher.NodeUtilities.getChildren;

public class Patcher {

    // TODO: Implement event-handlers
    // TODO: Implement change-listener

    private static final Logger LOG = LoggerFactory.getLogger(Patcher.class);

    public static void patch(Env env, Node root, VNode vRoot, Seq<Patch> patches, Consumer dispatcher) {
        for (final Patch patch : patches) {
            final Option<Node> optionalNode = findNode(patch.getIndex(), root, vRoot, 0);
            if (optionalNode.isEmpty()) {
                LOG.error("Unable to find node with index {}", patch.getIndex());
            } else {
                final Node node = optionalNode.get();
                switch (patch.getType()) {
                    case REPLACED:
                        doReplace(env, node, (ReplacePatch) patch, dispatcher);
                        break;
                    case ATTRIBUTES:
                        doAttributes(env, node, (AttributesPatch) patch, dispatcher);
                        break;
                    case ORDER:
                        throw new UnsupportedOperationException("Not implemented yet");
                    case INSERT:
                        doInsert(env, node, (InsertPatch) patch, dispatcher);
                        break;
                    case REMOVE:
                        doRemove(node);
                        break;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void doReplace(Env env, Node oldNode, ReplacePatch patch, Consumer dispatcher) {
        final Option<java.util.List<Node>> children = getChildren(oldNode.getParent());

        if (children.isDefined()) {
            final VNode vNode = patch.getNewNode();
            final Option<Node> newNode = NodeBuilder.create(env, vNode, dispatcher);
            if (newNode.isDefined()) {
                final int index = children.get().indexOf(oldNode);
                children.get().set(index, newNode.get());
                return;
            }
        }
        LOG.error("Unable to replace node from parent class {}", oldNode.getParent().getClass());
    }

    @SuppressWarnings("unchecked")
    private static void doAttributes(Env env, Node node, AttributesPatch patch, Consumer dispatcher) {
        NodeUtilities.setProperties(env, node, patch.getProperties(), dispatcher);
        NodeUtilities.setEventHandlers(node, patch.getEventHandlers(), dispatcher);
    }

    private static void doInsert(Env env, Node parent, InsertPatch patch, Consumer dispatcher) {
        final Option<java.util.List<Node>> children = getChildren(parent);

        if (children.isDefined()) {
            final VNode vNode = patch.getNewNode();
            final Option<Node> node = NodeBuilder.create(env, vNode, dispatcher);
            if (node.isDefined()) {
                children.get().add(node.get());
                return;
            }
        }
        LOG.error("Unable to add node to parent class {}", parent.getClass());
    }

    private static void doRemove(Node node) {
        final Option<java.util.List<Node>> children = getChildren(node.getParent());

        if (children.isDefined()) {
            children.get().remove(node);
        } else {
            LOG.error("Unable to remove node from parent class {}", node.getParent().getClass());
        }
    }

    private static Option<Node> findNode(int needle, Node node, VNode<?> vNode, int index) {
        if (needle == index) {
            return Option.of(node);
        }
        if (! (node instanceof Parent)) {
            LOG.error("Unable to navigate to children of class {}", node.getClass());
            return Option.none();
        }

        final Seq<Integer> sizes = vNode.getChildren()
                .map(VNode::getSize)
                .scan(index + 1, (a, b) -> a + b)
                .takeWhile(value -> value <= needle);
        final int child = sizes.length() - 1;

        return findNode(needle, ((Parent) node).getChildrenUnmodifiable().get(child), vNode.getChildren().get(child), sizes.last());
    }

}
