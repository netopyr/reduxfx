package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ToggleButtonBuilder<CLASS extends ToggleButtonBuilder<CLASS>> extends ButtonBaseBuilder<CLASS> {

    private static final String SELECTED = "selected";
    private static final String TOGGLE_GROUP = "toggleGroup";

    public ToggleButtonBuilder(Class<? extends Node> nodeClass,
                               Array<VProperty<?>> properties,
                               Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ToggleButtonBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS selected(boolean value) {
        return property(SELECTED, value);
    }

    public CLASS toggleGroup(String value) {
        return property(TOGGLE_GROUP, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
