package com.netopyr.reduxfx.impl.patcher.property;

import java.lang.invoke.MethodHandle;

abstract class AbstractNoConversionAccessor extends AbstractAccessor {

    AbstractNoConversionAccessor(MethodHandle propertyGetter) {
        super(propertyGetter);
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
