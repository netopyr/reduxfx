package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import com.netopyr.reduxfx.patcher.patches.AttributesPatch;
import com.netopyr.reduxfx.patcher.patches.InsertPatch;
import com.netopyr.reduxfx.patcher.patches.Patch;
import com.netopyr.reduxfx.patcher.patches.ReplacePatch;
import javafx.scene.Node;
import javafx.scene.Parent;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Patcher {

    // TODO: Implement event-handlers
    // TODO: Implement change-listener

    private static final Logger LOG = LoggerFactory.getLogger(Patcher.class);

    public static void patch(Node root, VNode vRoot, Seq<Patch> patches) {
        for (final Patch patch : patches) {
            final Option<Node> optionalNode = findNode(patch.getIndex(), root, vRoot, 0);
            if (optionalNode.isEmpty()) {
                LOG.error("Unable to find node with index {}", patch.getIndex());
            } else {
                final Node node = optionalNode.get();
                switch (patch.getType()) {
                    case REPLACED:
                        doReplace(node, (ReplacePatch) patch);
                        break;
                    case ATTRIBUTES:
                        doAttributes(node, (AttributesPatch) patch);
                        break;
                    case ORDER:
                        throw new UnsupportedOperationException("Not implemented yet");
                    case INSERT:
                        doInsert(node, (InsertPatch) patch);
                        break;
                    case REMOVE:
                        doRemove(node);
                        break;
                }
            }
        }
    }

    private static void doReplace(Node oldNode, ReplacePatch patch) {
        final Option<java.util.List<Node>> children = NodeBuilder.getChildren(oldNode.getParent());
        final VNode vNode = patch.getNewNode();

        if (children.isDefined()) {
            final Option<Node> newNode = NodeBuilder.create(vNode);
            if (newNode.isDefined()) {
                final int index = children.get().indexOf(oldNode);
                children.get().set(index, newNode.get());
                return;
            }
        }
        LOG.error("Unable to replace node from parent class {}", oldNode.getParent().getClass());
    }

    private static void doAttributes(Node node, AttributesPatch patch) {
        NodeUtilities.setProperties(node, patch.getProperties());
        NodeUtilities.setEventHandlers(node, patch.getEventHandlers());
        NodeUtilities.setChangeListeners(node, patch.getChangeListeners());
        NodeUtilities.setInvalidationListeners(node, patch.getInvalidationListeners());
    }

    private static void doInsert(Node parent, InsertPatch patch) {
        final Option<java.util.List<Node>> children = NodeBuilder.getChildren(parent);
        final VNode vNode = patch.getNewNode();

        if (children.isDefined()) {
            final Option<Node> node = NodeBuilder.create(vNode);
            if (node.isDefined()) {
                children.get().add(node.get());
                return;
            }
        }
        LOG.error("Unable to add node to parent class {}", parent.getClass());
    }

    private static void doRemove(Node node) {
        final Option<java.util.List<Node>> children = NodeBuilder.getChildren(node.getParent());

        if (children.isDefined()) {
            children.get().remove(node);
        } else {
            LOG.error("Unable to remove node from parent class {}", node.getParent().getClass());
        }
    }

    private static Option<Node> findNode(int needle, Node node, VNode vNode, int index) {
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
