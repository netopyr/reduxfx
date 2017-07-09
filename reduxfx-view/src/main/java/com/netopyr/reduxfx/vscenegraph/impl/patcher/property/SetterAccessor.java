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
                final Object value = vProperty.getValue();

                if (value instanceof int[]) {
                    setter.invoke(node, (int[]) value);
                } else if (value instanceof double[]) {
                    setter.invoke(node, (double[]) value);
                } else if (value instanceof long[]) {
                    setter.invoke(node, (long[]) value);
                } else if (value instanceof float[]) {
                    setter.invoke(node, (float[]) value);
                } else {
                    setter.invoke(node, value);
                }
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to set property " + name + " from Node-class " + node.getClass(), throwable);
            }
        }
    }
}
