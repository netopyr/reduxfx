package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ListAccessor<ACTION> extends AbstractNoConversionAccessor<ObservableList, ACTION> {

    public ListAccessor(MethodHandle methodHandle, Consumer<ACTION> dispatcher) {
        super(methodHandle, dispatcher);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<ObservableList> property, ObservableList value) {
        property.getValue().setAll(value);
    }
}
