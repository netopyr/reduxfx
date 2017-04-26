package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class LayoutConstraintAccessor implements Accessor {

    private final MethodHandle setter;

    LayoutConstraintAccessor(MethodHandle setter) {
        this.setter = setter;
    }

    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            try {
                setter.invoke(node, vProperty.getValue());
            } catch (Throwable e) {
                throw new IllegalStateException(String.format("Unable to set the layout constraint %s to %s in %s",
                        name, vProperty.getValue(), node), e);
            }
        }
    }
}
