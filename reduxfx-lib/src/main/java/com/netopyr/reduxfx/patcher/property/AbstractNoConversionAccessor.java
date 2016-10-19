package com.netopyr.reduxfx.patcher.property;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

abstract class AbstractNoConversionAccessor<TYPE, ACTION> extends AbstractAccessor<TYPE, ACTION, TYPE> {

    AbstractNoConversionAccessor(MethodHandle propertyGetter, Consumer<ACTION> dispatcher) {
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
