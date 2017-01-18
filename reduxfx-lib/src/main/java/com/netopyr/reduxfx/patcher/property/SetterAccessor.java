package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;

public class SetterAccessor<TYPE> implements Accessor<TYPE> {

    private final MethodHandle setter;

    SetterAccessor(MethodHandle setter) {
        this.setter = setter;
    }

    @Override
    public void set(Node node, VProperty<TYPE> vProperty) {
        if (vProperty.isValueDefined()) {
            try {
                setter.invoke(node, vProperty.getValue());
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to set property " + vProperty.getName() + " from Node-class " + node.getClass(), throwable);
            }
        }
    }
}
