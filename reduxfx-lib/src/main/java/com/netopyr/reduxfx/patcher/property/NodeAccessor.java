package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.patcher.NodeBuilder;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class NodeAccessor extends AbstractAccessor {

    private final NodeBuilder nodeBuilder;

    NodeAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher, NodeBuilder nodeBuilder) {
        super(propertyGetter, dispatcher);
        this.nodeBuilder = nodeBuilder;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object fxToV(Object value) {
        if (value instanceof Node) {
            final Node node = (Node) value;
            final Object vObj = node.getUserData();
            if (vObj instanceof VNode) {
                return vObj;
            }
        }
        throw new IllegalStateException(String.format("Unable to convert the value %s to a VNode", value));
    }

    @Override
    protected Object vToFX(Object value) {
        if (value instanceof VNode) {
            final Option<Node> nodeOption = nodeBuilder.create((VNode) value);
            if (nodeOption.isEmpty()) {
                return null;
            }

            final Node node = nodeOption.get();
            node.setUserData(value);
            return node;
        }
        throw new IllegalStateException(String.format("Unable to convert the value %s to a Node", value));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty property, Object obj) {
        if (obj instanceof Node) {
            final Node node = (Node) obj;
            final Object vObj = fxToV(node);
            if (vObj instanceof VNode) {
                final VNode vNode = (VNode) vObj;
                ((Property) property).setValue(node);
                nodeBuilder.init(node, vNode);
                return;
            }
        }
        throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
                obj, property.getName(), property.getBean().getClass()));
    }
}
