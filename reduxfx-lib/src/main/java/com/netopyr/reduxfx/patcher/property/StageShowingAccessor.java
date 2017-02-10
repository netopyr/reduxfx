package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.stage.Stage;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class StageShowingAccessor extends AbstractNoConversionAccessor {

    public StageShowingAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected void setValue(ReadOnlyProperty property, Object value) {
        final Stage stage = (Stage) property.getBean();
        if (Boolean.TRUE.equals(value)) {
            stage.show();
        } else {
            stage.hide();
        }
    }
}
