package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.patcher.NodeBuilder;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class NodeAccessor<ACTION> extends AbstractAccessor<VNode<ACTION>, ACTION, Node> {

    private final NodeBuilder<ACTION> nodeBuilder;

    NodeAccessor(MethodHandle propertyGetter, Consumer<ACTION> dispatcher, NodeBuilder<ACTION> nodeBuilder) {
        super(propertyGetter, dispatcher);
        this.nodeBuilder = nodeBuilder;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected VNode<ACTION> fxToV(Node value) {
        return (VNode<ACTION>) value.getUserData();
    }

    @Override
    protected Node vToFX(VNode<ACTION> value) {
        final Option<Node> nodeOption = nodeBuilder.create(value);
        if (nodeOption.isEmpty()) {
            return null;
        }

        final Node node = nodeOption.get();
        node.setUserData(value);
        nodeBuilder.init(node, value);
        return node;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<Node> property, Node value) {
        ((Property)property).setValue(value);
    }
}
