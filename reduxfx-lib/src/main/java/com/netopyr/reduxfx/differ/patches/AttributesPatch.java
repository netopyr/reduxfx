package com.netopyr.reduxfx.differ.patches;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AttributesPatch extends Patch {

    private final Map<String, VProperty<?>> properties;
    private final Map<VEventType, VEventHandlerElement<?>> eventHandlers;

    public AttributesPatch(
            int index,
            Map<String, VProperty<?>> properties,
            Map<VEventType, VEventHandlerElement<?>> eventHandlers) {
        super(index);
        this.properties = properties;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public Type getType() {
        return Type.ATTRIBUTES;
    }

    public Map<String, VProperty<?>> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandlerElement<?>> getEventHandlers() {
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
