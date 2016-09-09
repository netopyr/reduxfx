package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(NodeBuilder.class);

    public static Option<Node> create(VNode vNode) {
        try {
            final Class<? extends Node> nodeClass = vNode.getType().getNodeClass();

            final Node node = nodeClass.newInstance();

            NodeUtilities.setAllAttributes(node, vNode);

            if (vNode.getChildren().nonEmpty()) {
                final Option<java.util.List<Node>> children = getChildren(node);
                if (children.isEmpty()) {
                    LOG.error("VNode has children defined, but is neither a Group nor a Pane: {}", vNode);
                    return Option.none();
                }

                final Seq<Option<Node>> vChildren = vNode.getChildren().map(NodeBuilder::create);
                if (vChildren.find(Option::isEmpty).isDefined()) {
                    // one of the children could not be created
                    return Option.none();
                }
                children.get().addAll(vChildren.map(Option::get).toJavaList());
            }

            vNode.getRef().peek(ref -> ref.accept(node));

            return Option.of(node);

        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Unable to create node", e);
            return Option.none();
        }
    }

    public static Option<java.util.List<Node>> getChildren(Node node) {
        return node instanceof Group? Option.of(((Group) node).getChildren())
                : node instanceof Pane? Option.of(((Pane) node).getChildren())
                : Option.none();
    }
}
