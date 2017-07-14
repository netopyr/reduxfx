package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("SameParameterValue")
public class TextInputControlBuilder<B extends TextInputControlBuilder<B>> extends ControlBuilder<B> {

    private static final String PROMPT_TEXT = "promptText";
    private static final String TEXT = "text";

    public TextInputControlBuilder(Class<?> nodeClass,
                                   Map<String, Array<VNode>> childrenMap,
                                   Map<String, Option<VNode>> singleChildMap,
                                   Map<String, VProperty> properties,
                                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new TextInputControlBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B promptText(String value) {
        return property(PROMPT_TEXT, value);
    }

    public B text(String value, VChangeListener<? super String> listener) {
        return property(TEXT, value, listener);
    }
    public B text(String value) {
        return property(TEXT, value);
    }
    public B text(VChangeListener<? super String> listener) {
        return property(TEXT, listener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
