package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ReadOnlyPropertyAccessor<TYPE> extends AbstractNoConversionAccessor<TYPE> {

    ReadOnlyPropertyAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected void setValue(ReadOnlyProperty property, Object value) {

    }
}
