package com.netopyr.reduxfx.differ.patches;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AttributesPatch extends Patch {

    private final Map<String, VProperty> properties;
    private final Map<VEventType, Option<VEventHandler>> eventHandlers;

    public AttributesPatch(
            int index,
            Map<String, VProperty> properties,
            Map<VEventType, Option<VEventHandler>> eventHandlers) {
        super(index);
        this.properties = properties;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public Type getType() {
        return Type.ATTRIBUTES;
    }

    public Map<String, VProperty> getProperties() {
        return properties;
    }

    public Map<VEventType, Option<VEventHandler>> getEventHandlers() {
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
