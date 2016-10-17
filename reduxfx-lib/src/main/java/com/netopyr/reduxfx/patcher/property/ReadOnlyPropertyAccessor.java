package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ReadOnlyPropertyAccessor<TYPE, ACTION> extends AbstractAccessor<TYPE, ACTION, TYPE> {

    ReadOnlyPropertyAccessor(MethodHandle propertyGetter, Consumer<ACTION> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected TYPE fxToV(TYPE value) {
        return value;
    }


    @Override
    protected TYPE vToFX(TYPE value) {
        return value;
    }

    @Override
    protected void setValue(ReadOnlyProperty property, Object value) {

    }
}
