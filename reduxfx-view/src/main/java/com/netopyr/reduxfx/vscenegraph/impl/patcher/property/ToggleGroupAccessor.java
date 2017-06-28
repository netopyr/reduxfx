package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import java.util.function.Consumer;

public class ToggleGroupAccessor extends AbstractAccessor {

    private Map<Object, ToggleGroup> mapping = HashMap.empty();

    public ToggleGroupAccessor() {
        super(NodeUtilities.getPropertyGetter(ToggleButton.class, "toggleGroup").get());
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
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        if (value instanceof ToggleGroup) {
            ((Property) property).setValue(value);
            return;
        }
        throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
                value, property.getName(), property.getBean().getClass()));
    }
}
