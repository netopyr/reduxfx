package com.netopyr.reduxfx.differ.patches;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class AttributesPatch extends Patch {

    private final Map<String, VProperty> properties;
    private final Map<VEventType, Option<VEventHandler>> eventHandlers;

    public AttributesPatch(
            int index,
            Map<String, VProperty> properties,
            Map<VEventType, Option<VEventHandler>> eventHandlers) {
        super(index);
        this.properties = Objects.requireNonNull(properties, "Properties must not be null");
        this.eventHandlers = Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");
    }

    public AttributesPatch(int index, String name, VProperty property) {
        this(index, HashMap.of(name, property), HashMap.empty());
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
