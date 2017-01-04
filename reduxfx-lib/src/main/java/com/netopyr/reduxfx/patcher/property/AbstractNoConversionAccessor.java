package com.netopyr.reduxfx.patcher.property;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

abstract class AbstractNoConversionAccessor<TYPE> extends AbstractAccessor<TYPE, TYPE> {

    AbstractNoConversionAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
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
}
