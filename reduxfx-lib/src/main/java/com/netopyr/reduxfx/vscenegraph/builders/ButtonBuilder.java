package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ButtonBuilder<CLASS extends ButtonBuilder<CLASS>> extends ButtonBaseBuilder<CLASS> {

    public static final String DEFAULT_BUTTON = "defaultButton";

    public ButtonBuilder(Class<? extends Node> nodeClass,
                         Array<VProperty<?>> properties,
                         Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ButtonBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS defaultButton(boolean value) {
        return property(DEFAULT_BUTTON, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
