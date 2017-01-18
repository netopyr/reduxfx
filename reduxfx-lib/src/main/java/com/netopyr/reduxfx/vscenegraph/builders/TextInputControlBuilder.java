package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextInputControlBuilder<CLASS extends TextInputControlBuilder<CLASS>> extends ControlBuilder<CLASS> {

    private static final String PROMPT_TEXT = "promptText";
    private static final String TEXT = "text";

    public TextInputControlBuilder(Class<? extends Node> nodeClass,
                                   Array<VProperty<?>> properties,
                                   Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new TextInputControlBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS promptText(String value) {
        return property(PROMPT_TEXT, value);
    }

    public CLASS text(String value, VChangeListener<? super String> listener) {
        return property(TEXT, value, listener);
    }
    public CLASS text(String value) {
        return property(TEXT, value);
    }
    public CLASS text(VChangeListener<? super String> listener) {
        return property(TEXT, listener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
