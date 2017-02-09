package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.ActionEvent;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.ACTION;

public class ButtonBaseBuilder<BUILDER extends ButtonBaseBuilder<BUILDER>> extends LabeledBuilder<BUILDER> {

    private static final String TEXT = "text";

    public ButtonBaseBuilder(Class<?> nodeClass,
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
        return (BUILDER) new ButtonBaseBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
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
