package com.netopyr.reduxfx.impl.patcher.property;

import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ReadOnlyPropertyAccessor extends AbstractNoConversionAccessor {

    ReadOnlyPropertyAccessor(MethodHandle propertyGetter) {
        super(propertyGetter);
    }

    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {

    }
}
