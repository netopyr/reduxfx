package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.ACTION;

public class ButtonBaseBuilder<BUILDER extends ButtonBaseBuilder<BUILDER>> extends LabeledBuilder<BUILDER> {

    private static final String TEXT = "text";

    public ButtonBaseBuilder(Class<? extends Node> nodeClass,
                             Array<VProperty<?>> properties,
                             Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new ButtonBaseBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER text(String value) {
        return property(TEXT, value);
    }


    public BUILDER onAction(VEventHandler<ActionEvent> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
