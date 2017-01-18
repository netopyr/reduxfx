package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.ACTION;

public class TextFieldBuilder<CLASS extends TextFieldBuilder<CLASS>> extends TextInputControlBuilder<CLASS> {

    public TextFieldBuilder(Class<? extends Node> nodeClass,
                            Array<VProperty<?>> properties,
                            Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new TextFieldBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS onAction(VEventHandler<ActionEvent> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
