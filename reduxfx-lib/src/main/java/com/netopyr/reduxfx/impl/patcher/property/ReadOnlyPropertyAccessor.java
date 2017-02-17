package com.netopyr.reduxfx.impl.patcher.property;

import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ReadOnlyPropertyAccessor extends AbstractNoConversionAccessor {

    ReadOnlyPropertyAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected void setValue(ReadOnlyProperty property, Object value) {

    }
}
