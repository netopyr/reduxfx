package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.beans.property.Property;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

import java.util.function.Consumer;

public class ToggleGroupAccessor<ACTION> extends AbstractAccessor<String, ACTION, ToggleGroup> {

    private Map<String, ToggleGroup> mapping = HashMap.empty();

    public ToggleGroupAccessor(Class<? extends Node> clazz, Consumer<ACTION> dispatcher) {
        super(clazz, VPropertyType.TOGGLE_GROUP, dispatcher);
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

    @Override
    protected void setValue(Property<ToggleGroup> property, ToggleGroup value) {
        property.setValue(value);
    }
}
