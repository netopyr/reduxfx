package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextInputControlBuilder<BUILDER extends TextInputControlBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

    private static final String PROMPT_TEXT = "promptText";
    private static final String TEXT = "text";

    public TextInputControlBuilder(Class<?> nodeClass,
                                   Array<VNode> children,
                                   Map<String, VProperty> namedChildren,
                                   Map<String, VProperty> properties,
                                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new TextInputControlBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER promptText(String value) {
        return property(PROMPT_TEXT, value);
    }

    public BUILDER text(String value, VChangeListener<? super String> listener) {
        return property(TEXT, value, listener);
    }
    public BUILDER text(String value) {
        return property(TEXT, value);
    }
    public BUILDER text(VChangeListener<? super String> listener) {
        return property(TEXT, listener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
