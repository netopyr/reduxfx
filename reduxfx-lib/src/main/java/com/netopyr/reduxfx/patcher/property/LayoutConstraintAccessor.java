package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;

public class LayoutConstraintAccessor implements Accessor {

    private final MethodHandle setter;

    LayoutConstraintAccessor(MethodHandle setter) {
        this.setter = setter;
    }

    @Override
    public void set(Node node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            try {
                setter.invoke(node, vProperty.getValue());
            } catch (Throwable e) {
                throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
                        vProperty.getValue(), name, node.getParent().getClass()), e);
            }
        }
    }
}
