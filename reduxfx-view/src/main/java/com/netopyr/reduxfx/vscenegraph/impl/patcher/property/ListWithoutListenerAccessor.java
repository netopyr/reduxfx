package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.ObservableList;
import io.vavr.collection.Seq;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.function.Consumer;

public class ListWithoutListenerAccessor implements Accessor {

    private final MethodHandle getter;

    ListWithoutListenerAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            final ObservableList list;
            try {
                list = (ObservableList) getter.invoke(node);
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to read value of property " + name + " from Node-class " + node.getClass(), throwable);
            }

            list.setAll(vProperty.getValue() == null ? Collections.emptyList() : ((Seq) vProperty.getValue()).toJavaList());
        }
    }
}
