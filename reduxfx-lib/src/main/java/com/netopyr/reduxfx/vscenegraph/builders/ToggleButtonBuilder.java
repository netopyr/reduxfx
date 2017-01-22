package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ToggleButtonBuilder<BUILDER extends ToggleButtonBuilder<BUILDER>> extends ButtonBaseBuilder<BUILDER> {

    private static final String SELECTED = "selected";
    private static final String TOGGLE_GROUP = "toggleGroup";

    public ToggleButtonBuilder(Class<? extends Node> nodeClass,
                               Array<VProperty<?>> properties,
                               Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new ToggleButtonBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER selected(boolean value) {
        return property(SELECTED, value);
    }

    public BUILDER toggleGroup(String value) {
        return property(TOGGLE_GROUP, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
