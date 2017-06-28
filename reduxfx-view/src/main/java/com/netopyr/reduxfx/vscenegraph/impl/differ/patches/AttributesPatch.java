package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class AttributesPatch extends Patch {

    private final Map<String, VProperty> properties;
    private final Map<VEventType, Option<VEventHandler>> eventHandlers;

    public AttributesPatch(
            Vector<Object> path,
            Map<String, VProperty> properties,
            Map<VEventType, Option<VEventHandler>> eventHandlers) {
        super(path);
        this.properties = Objects.requireNonNull(properties, "Properties must not be null");
        this.eventHandlers = Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");
    }

    public AttributesPatch(Vector<Object> path, String name, VProperty property) {
        this(path, HashMap.of(name, property), HashMap.empty());
    }

    public Map<String, VProperty> getProperties() {
        return properties;
    }

    public Map<VEventType, Option<VEventHandler>> getEventHandlers() {
        return eventHandlers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }
}
