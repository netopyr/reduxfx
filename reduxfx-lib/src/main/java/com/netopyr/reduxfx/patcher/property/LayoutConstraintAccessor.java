package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodType.methodType;

public class LayoutConstraintAccessor<TYPE, ACTION> implements PropertyAccessor<TYPE, ACTION> {

    private final MethodHandle setter;

    public LayoutConstraintAccessor(Class<? extends Pane> parentClass, VPropertyType propertyType, Class<TYPE> valueClass) {
        this.setter = getSetter(parentClass, propertyType, valueClass);
    }

    @Override
    public void set(Node node, VProperty<TYPE, ACTION> vProperty) {
        try {
            this.setter.invoke(node, vProperty.getValue());
        } catch (Throwable throwable) {
            throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
                    vProperty.getValue(), vProperty.getType().getName(), node.getParent().getClass()));
        }
    }

    private MethodHandle getSetter(Class<? extends Pane> parentClass, VPropertyType propertyType, Class<?> valueClass) {
        final String propertyName = propertyType.getName();
        final String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        try {
            return MethodHandles.publicLookup().findStatic(parentClass, setterName, methodType(void.class, Node.class, valueClass));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Unable to find setter of property " + propertyName + " in class " + parentClass, e);
        }
    }
}
