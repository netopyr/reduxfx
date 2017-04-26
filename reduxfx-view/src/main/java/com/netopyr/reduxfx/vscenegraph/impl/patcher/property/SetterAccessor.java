package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class SetterAccessor implements Accessor {

    private final MethodHandle setter;

    SetterAccessor(MethodHandle setter) {
        this.setter = setter;
    }

    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            try {
                setter.invoke(node, vProperty.getValue());
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to set property " + name + " from Node-class " + node.getClass(), throwable);
            }
        }
    }
}
