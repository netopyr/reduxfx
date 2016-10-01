package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Node;
import javaslang.collection.Array;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static com.netopyr.reduxfx.patcher.NodeUtilities.getChildren;

class NodeBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(NodeBuilder.class);

    @SuppressWarnings("unchecked")
    static Option<Node> create(VNode vNode, Consumer dispatcher) {
        try {
            final Class<? extends Node> nodeClass = vNode.getType().getNodeClass();

            final Node node = nodeClass.newInstance();

            NodeUtilities.setAllAttributes(node, vNode, dispatcher);

            if (vNode.getChildren().nonEmpty()) {
                final Option<java.util.List<Node>> children = getChildren(node);
                if (children.isEmpty()) {
                    LOG.error("VNode has children defined, but is neither a Group nor a Pane: {}", vNode);
                    return Option.none();
                }

                final Array<Option<Node>> vChildren = vNode.getChildren().map(child -> create((VNode)child, dispatcher));
                if (vChildren.find(Option::isEmpty).isDefined()) {
                    // one of the children could not be created
                    return Option.none();
                }
                children.get().addAll(vChildren.map(Option::get).toJavaList());
            }

            return Option.of(node);

        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Unable to create node", e);
            return Option.none();
        }
    }
}
