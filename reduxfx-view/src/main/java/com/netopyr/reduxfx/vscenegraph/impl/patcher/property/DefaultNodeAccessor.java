package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeBuilder;
import io.vavr.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class DefaultNodeAccessor implements NodeAccessor {

    private final MethodHandle setter;

    public DefaultNodeAccessor(MethodHandle setter) {
        this.setter = setter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object parent, String name, Option<VNode> vNode) {
        if (vNode.isDefined()) {
            final Option<Object> nodeOption = NodeBuilder.create(vNode.get());
            if (nodeOption.isDefined()) {
                try {
                    setter.invoke(parent, nodeOption.get());
                } catch (Throwable throwable) {
                    throw new IllegalStateException("Unable to set property " + name + " of class " + parent.getClass(), throwable);
                }
                NodeBuilder.init(dispatcher, nodeOption.get(), vNode.get());
            } else {
                throw new IllegalStateException(String.format("Unable to convert the value %s to a Node", vNode.get()));
            }
        } else {
            try {
                setter.invoke(parent, null);
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to set property " + name + " of class " + parent.getClass(), throwable);
            }
        }
    }
}
