package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ReadOnlyPropertyAccessor extends AbstractNoConversionAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(ReadOnlyPropertyAccessor.class);

    ReadOnlyPropertyAccessor(MethodHandle propertyGetter) {
        super(propertyGetter);
    }

    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        LOG.warn("Tried to set read-only property {} to {}", property, value);
    }
}
