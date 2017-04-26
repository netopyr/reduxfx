package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class PropertyAccessor extends AbstractNoConversionAccessor {

    PropertyAccessor(MethodHandle propertyGetter) {
        super(propertyGetter);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty readOnlyProperty, Object value) {
        final Property property = (Property) readOnlyProperty;
        if (property.getValue() == null? value != null : ! property.getValue().equals(value)) {
            property.setValue(value);
        }
    }
}
