package com.netopyr.reduxfx.patcher.property;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

abstract class AbstractNoConversionAccessor extends AbstractAccessor {

    AbstractNoConversionAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected Object fxToV(Object value) {
        return value;
    }

    @Override
    protected Object vToFX(Object value) {
        return value;
    }
}
