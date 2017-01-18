package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class FocusedAccessor extends AbstractNoConversionAccessor<Boolean> {

    FocusedAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected void setValue(ReadOnlyProperty<Boolean> property, Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            ((Node) property.getBean()).requestFocus();
        }
    }
}
