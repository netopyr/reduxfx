package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LabeledBuilder<CLASS extends LabeledBuilder<CLASS>> extends ControlBuilder<CLASS> {

    private static final String MNEMONIC_PARSING = "mnemonicParsing";
    private static final String TEXT = "text";
    private static final String GRAPHIC = "graphic";
    private static final String TEXT_FILL = "textFill";
    private static final String WRAP_TEXT = "wrapText";

    public LabeledBuilder(Class<? extends Node> nodeClass,
                          Array<VProperty<?>> properties,
                          Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new LabeledBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS graphic(VNode value) {
        return property(GRAPHIC, value);
    }

    public CLASS mnemonicParsing(boolean value) {
        return property(MNEMONIC_PARSING, value);
    }

    public CLASS text(String value) {
        return property(TEXT, value);
    }

    public CLASS textFill(Paint value) {
        return property(TEXT_FILL, value);
    }

    public CLASS wrapText(boolean value) {
        return property(WRAP_TEXT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
