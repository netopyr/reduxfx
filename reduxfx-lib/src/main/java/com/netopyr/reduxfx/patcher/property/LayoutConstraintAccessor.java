package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;

public class LayoutConstraintAccessor<ACTION> implements Accessor<Object, ACTION> {

    private final MethodHandle setter;

    public LayoutConstraintAccessor(MethodHandle setter) {
        this.setter = setter;
    }

    @Override
    public void set(Node node, VProperty<Object, ACTION> vProperty) {
        try {
            setter.invoke(node, vProperty.getValue());
        } catch (Throwable e) {
            throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
                    vProperty.getValue(), vProperty.getName(), node.getParent().getClass()), e);
        }
    }
}
