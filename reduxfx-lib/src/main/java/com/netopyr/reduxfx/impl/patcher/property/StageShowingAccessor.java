package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.impl.patcher.NodeUtilities;
import javafx.beans.property.ReadOnlyProperty;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class StageShowingAccessor extends AbstractNoConversionAccessor {

    public StageShowingAccessor() {
        super(NodeUtilities.getPropertyGetter(Stage.class, "showing").get());
    }

    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        final Stage stage = (Stage) property.getBean();
        if (Boolean.TRUE.equals(value)) {
            stage.show();
        } else {
            stage.hide();
        }
    }
}
