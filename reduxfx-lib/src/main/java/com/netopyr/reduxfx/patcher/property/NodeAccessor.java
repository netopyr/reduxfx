package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.patcher.NodeBuilder;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class NodeAccessor extends AbstractAccessor<VNode, Node> {

    private final NodeBuilder nodeBuilder;

    NodeAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher, NodeBuilder nodeBuilder) {
        super(propertyGetter, dispatcher);
        this.nodeBuilder = nodeBuilder;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected VNode fxToV(Node value) {
        return (VNode) value.getUserData();
    }

    @Override
    protected Node vToFX(VNode value) {
        final Option<Node> nodeOption = nodeBuilder.create(value);
        if (nodeOption.isEmpty()) {
            return null;
        }

        final Node node = nodeOption.get();
        node.setUserData(value);
        return node;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<Node> property, Node node) {
        ((Property)property).setValue(node);
        nodeBuilder.init(node, fxToV(node));
    }
}
