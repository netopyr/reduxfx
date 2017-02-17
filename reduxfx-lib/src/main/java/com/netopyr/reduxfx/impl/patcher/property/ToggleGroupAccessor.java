package com.netopyr.reduxfx.impl.patcher.property;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

public class ToggleGroupAccessor extends AbstractAccessor {

    private Map<Object, ToggleGroup> mapping = HashMap.empty();

    public ToggleGroupAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        super(propertyGetter, dispatcher);
    }

    @Override
    protected Object fxToV(Object value) {
        if (value instanceof Node) {
            final Node node = (Node) value;
            final Object vObj = node.getUserData();
            if (vObj instanceof String) {
                return vObj;
            }
        }
        throw new IllegalStateException(String.format("Unable to convert the value %s to a String", value));
    }

    @Override
    protected Object vToFX(Object key) {
        if (key instanceof String) {
            final Option<ToggleGroup> found = mapping.get(key);
            if (found.isDefined()) {
                return found.get();
            }

            final ToggleGroup toggleGroup = new ToggleGroup();
            toggleGroup.setUserData(key);
            mapping = mapping.put(key, toggleGroup);
            return toggleGroup;
        }
        throw new IllegalStateException(String.format("Unable to convert the value %s to a ToggleGroup", key));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(ReadOnlyProperty property, Object value) {
        if (value instanceof ToggleGroup) {
            ((Property) property).setValue(value);
            return;
        }
        throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
                value, property.getName(), property.getBean().getClass()));
    }
}
