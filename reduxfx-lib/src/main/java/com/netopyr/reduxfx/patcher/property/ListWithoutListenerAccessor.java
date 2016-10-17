package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;
import java.util.Collections;

public class ListWithoutListenerAccessor<ACTION> implements Accessor<ObservableList, ACTION> {

    private final MethodHandle getter;

    public ListWithoutListenerAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Node node, VProperty<ObservableList, ACTION> vProperty) {
        final ObservableList list;
        try {
            list = (ObservableList) getter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read value of property " + vProperty.getName() + " from Node-class " + node.getClass(), throwable);
        }

        list.setAll(vProperty.getValue() == null? Collections.emptyList() : vProperty.getValue());
    }
}
