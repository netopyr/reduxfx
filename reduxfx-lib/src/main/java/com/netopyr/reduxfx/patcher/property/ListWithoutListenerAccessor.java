package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Collections;

public class ListWithoutListenerAccessor<ACTION> implements PropertyAccessor<ObservableList, ACTION> {

    private final MethodHandle getter;

    public ListWithoutListenerAccessor(Class<? extends Node> clazz, VPropertyType propertyType) {
        this.getter = getGetter(clazz, propertyType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Node node, VProperty<ObservableList, ACTION> vProperty) {
        final ObservableList list;
        try {
            list = (ObservableList) getter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read value of property " + vProperty.getType() + " from Node-class " + node.getClass(), throwable);
        }

        list.setAll(vProperty.getValue() == null? Collections.emptyList() : vProperty.getValue());
    }

    private MethodHandle getGetter(Class<? extends Node> clazz, VPropertyType propertyType) {
        final String propertyName = propertyType.getName();
        final String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        final Method method;
        try {
            method = clazz.getMethod(getterName);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to find getter of property " + propertyName + " in class " + clazz, e);
        }

        try {
            return MethodHandles.publicLookup().unreflect(method);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Getter of property " + propertyName + " in class " + clazz + " is not accessible", e);
        }
    }
}
