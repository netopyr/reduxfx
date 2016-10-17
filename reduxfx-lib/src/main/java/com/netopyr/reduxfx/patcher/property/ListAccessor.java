package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ListAccessor<ACTION> extends AbstractAccessor<ObservableList, ACTION, ObservableList> {

    public ListAccessor(MethodHandle methodHandle, Consumer<ACTION> dispatcher) {
        super(methodHandle, dispatcher);
    }

    @Override
    protected ObservableList fxToV(ObservableList value) {
        return value;
    }

    @Override
    protected ObservableList vToFX(ObservableList value) {
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<ObservableList> property, ObservableList value) {
        property.getValue().setAll(value);
    }
}
