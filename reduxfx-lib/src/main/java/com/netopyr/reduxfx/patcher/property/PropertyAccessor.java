package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class PropertyAccessor<TYPE, ACTION> extends AbstractNoConversionAccessor<TYPE, ACTION> {

    PropertyAccessor(MethodHandle propertyGetter, Consumer<ACTION> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<TYPE> property, TYPE value) {
        ((Property)property).setValue(value);
    }
}
