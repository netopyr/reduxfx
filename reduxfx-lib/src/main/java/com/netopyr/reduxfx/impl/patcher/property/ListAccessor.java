package com.netopyr.reduxfx.impl.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import java.lang.invoke.MethodHandle;
import java.util.Collection;
import java.util.function.Consumer;

public class ListAccessor extends AbstractNoConversionAccessor {

    ListAccessor(MethodHandle methodHandle, Consumer<Object> dispatcher) {
        super(methodHandle, dispatcher);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty property, Object value) {
        final Object propertyValue = property.getValue();
        if (propertyValue instanceof ObservableList && value instanceof Collection) {
            ((ObservableList) property.getValue()).setAll((Collection) value);
        }
    }
}
