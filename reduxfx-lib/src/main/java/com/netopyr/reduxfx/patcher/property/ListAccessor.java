package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ListAccessor extends AbstractNoConversionAccessor<ObservableList> {

    public ListAccessor(MethodHandle methodHandle, Consumer<Object> dispatcher) {
        super(methodHandle, dispatcher);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<ObservableList> property, ObservableList value) {
        property.getValue().setAll(value);
    }
}
