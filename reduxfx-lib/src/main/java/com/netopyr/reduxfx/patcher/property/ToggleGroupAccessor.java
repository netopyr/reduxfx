package com.netopyr.reduxfx.patcher.property;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.control.ToggleGroup;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ToggleGroupAccessor<ACTION> extends AbstractAccessor<String, ACTION, ToggleGroup> {

    private Map<String, ToggleGroup> mapping = HashMap.empty();

    public ToggleGroupAccessor(MethodHandle propertyGetter, Consumer<ACTION> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected String fxToV(ToggleGroup value) {
        return (String) value.getUserData();
    }

    @Override
    protected ToggleGroup vToFX(String key) {
        final Option<ToggleGroup> found = mapping.get(key);
        if (found.isDefined()) {
            return found.get();
        }

        final ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.setUserData(key);
        mapping = mapping.put(key, toggleGroup);
        return toggleGroup;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty<ToggleGroup> property, ToggleGroup value) {
        ((Property)property).setValue(value);
    }
}
