package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.patcher.property.LayoutConstraintAccessor;
import com.netopyr.reduxfx.patcher.property.ListAccessor;
import com.netopyr.reduxfx.patcher.property.ListWithoutListenerAccessor;
import com.netopyr.reduxfx.patcher.property.PropertyAccessor;
import com.netopyr.reduxfx.patcher.property.ToggleGroupAccessor;
import com.netopyr.reduxfx.patcher.property.ValueAccessor;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Map;

import java.util.function.Consumer;

public class PropertySetter<ACTION> {

    private final java.util.Map<Tuple2<Class<? extends Node>, VPropertyType>, PropertyAccessor> accessors = new java.util.HashMap<>();

    private final Consumer<ACTION> dispatcher;

    public PropertySetter(Consumer<ACTION> dispatcher) {
        this.dispatcher = dispatcher;
    }

    @SuppressWarnings("unchecked")
    void setProperties(Node node, Map<VPropertyType, VProperty<?, ACTION>> properties) {
        for (final VProperty<?, ACTION> vProperty : properties.values()) {
            final PropertyAccessor accessor = accessors.computeIfAbsent(
                    Tuple.of(node.getClass(), vProperty.getType()),
                    this::createPropertyAccessor
            );
            accessor.set(node, vProperty);
        }
    }

    private PropertyAccessor createPropertyAccessor(Tuple2<Class<? extends Node>, VPropertyType> key) {
        final Class<? extends Node> clazz = key._1;
        final VPropertyType type = key._2;

        switch (type) {
            case ITEMS:
                return new ListAccessor<>(clazz, type, dispatcher);
            case STYLE_CLASS:
            case STYLESHEETS:
                return new ListWithoutListenerAccessor<>(clazz, type);
            case H_GROW:
                return new LayoutConstraintAccessor<>(HBox.class, type, Priority.class);
            case TOP_ANCHOR:
            case RIGHT_ANCHOR:
            case BOTTOM_ANCHOR:
            case LEFT_ANCHOR:
                return new LayoutConstraintAccessor<>(AnchorPane.class, type, Double.class);
            case TOGGLE_GROUP:
                return new ToggleGroupAccessor<>(clazz, dispatcher);
            default:
                return new ValueAccessor<>(clazz, type, dispatcher);
        }
    }

//    @SuppressWarnings("unchecked")
//    private static void setPropertyToggleGroup(Env env, Property property, Object newValue) {
//        property.setValue(newValue != null && String.class.equals(newValue.getClass())? env.getToggleGroup((String) newValue) : null);
//    }
//
}
