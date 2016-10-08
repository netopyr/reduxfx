package com.netopyr.reduxfx.differ.patches;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AttributesPatch<ACTION> extends Patch {

    private final Map<VPropertyType, VProperty<?, ACTION>> properties;
    private final Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers;

    public AttributesPatch(
            int index,
            Map<VPropertyType, VProperty<?, ACTION>> properties,
            Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers) {
        super(index);
        this.properties = properties;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public Type getType() {
        return Type.ATTRIBUTES;
    }

    public Map<VPropertyType, VProperty<?, ACTION>> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandlerElement<?, ACTION>> getEventHandlers() {
        return eventHandlers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }
}
