package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.ObservableList;

import java.lang.invoke.MethodHandle;
import java.util.Collection;
import java.util.Collections;

public class ListWithoutListenerAccessor implements Accessor {

    private final MethodHandle getter;

    ListWithoutListenerAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            final ObservableList list;
            try {
                list = (ObservableList) getter.invoke(node);
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to read value of property " + name + " from Node-class " + node.getClass(), throwable);
            }

            list.setAll(vProperty.getValue() == null ? Collections.emptyList() : (Collection) vProperty.getValue());
        }
    }
}
