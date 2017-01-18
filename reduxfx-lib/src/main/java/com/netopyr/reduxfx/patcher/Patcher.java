package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.differ.patches.InsertPatch;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.patcher.property.Accessors;
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

    private static final Logger LOG = LoggerFactory.getLogger(Patcher.class);

    private final NodeBuilder nodeBuilder;

    public Patcher(Consumer<Object> dispatcher) {
        final Accessors accessors = new Accessors(dispatcher);
        this.nodeBuilder = new NodeBuilder(dispatcher, accessors);
        accessors.init(nodeBuilder);

        INSTANCE = this;
    }

    public void patch(Parent root, Option<VNode> vRoot, Seq<Patch> patches) {

        LOG.trace("Patches:\n{}", patches);

        for (final Patch patch : patches) {
            final Node node = vRoot.flatMap(vNode -> findNode(patch.getIndex(), root.getChildrenUnmodifiable().get(0), vNode, 0)).getOrElse(root);

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

    @SuppressWarnings("unchecked")
    private void doReplace(Node oldNode, ReplacePatch patch) {
        final Option<java.util.List<Node>> children = getChildren(oldNode.getParent());

        if (children.isDefined()) {
            final VNode vNode = patch.getNewNode();
            final Option<Node> newNode = nodeBuilder.create(vNode);
            if (newNode.isDefined()) {
                final int index = children.get().indexOf(oldNode);
                children.get().set(index, newNode.get());
                nodeBuilder.init(newNode.get(), vNode);
                return;
            }
        }
        LOG.error("Unable to replace node from parent class {}", oldNode.getParent().getClass());
    }

    @SuppressWarnings("unchecked")
    private void doAttributes(Node node, AttributesPatch patch) {
        nodeBuilder.updateProperties(node, patch.getProperties());
        nodeBuilder.updateEventHandlers(node, patch.getEventHandlers());
    }

    @SuppressWarnings("unchecked")
    private void doInsert(Node parent, InsertPatch patch) {
        final Option<java.util.List<Node>> children = getChildren(parent);

        if (children.isDefined()) {
            final VNode vNode = patch.getNewNode();
            final Option<Node> node = nodeBuilder.create(vNode);
            if (node.isDefined()) {
                children.get().add(node.get());
                nodeBuilder.init(node.get(), vNode);
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



    private static Patcher INSTANCE;
    public static Patcher getInstance() {
        return INSTANCE;
    }
}
