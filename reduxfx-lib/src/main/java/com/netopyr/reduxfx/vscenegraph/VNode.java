package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class VNode {

    private final Class<?> nodeClass;
    private final Array<VNode> children;
    private final Map<String, VProperty> namedChildren;
    private final Map<String, VProperty> properties;
    private final Map<VEventType, VEventHandler> eventHandlers;


    @SuppressWarnings("unchecked")
    public VNode(Class<?> nodeClass,
                 Array<VNode> children,
                 Map<String, VProperty> namedChildren,
                 Map<String, VProperty> properties,
                 Map<VEventType, VEventHandler> eventHandlers) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "NodeClass must not be null");
        this.children = Objects.requireNonNull(children, "Children must not be null");
        this.namedChildren = Objects.requireNonNull(namedChildren, "NamedChildren must not be null");
        this.properties = Objects.requireNonNull(properties, "Properties must not be null");
        this.eventHandlers = Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");
    }


    public Class<?> getNodeClass() {
        return nodeClass;
    }

    public Array<VNode> getChildren() {
        return children;
    }

    public Map<String, VProperty> getNamedChildren() {
        return namedChildren;
    }

    public Map<String, VProperty> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandler> getEventHandlers() {
        return eventHandlers;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nodeClass", nodeClass)
                .append("children", children)
                .append("namedChildren", namedChildren)
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }
}
