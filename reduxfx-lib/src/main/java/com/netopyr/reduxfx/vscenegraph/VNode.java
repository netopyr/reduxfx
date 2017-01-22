package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class VNode {

    private final Class<? extends Node> nodeClass;
    private final Array<VNode> children;
    private final Map<String, VProperty> properties;
    private final Map<VEventType, VEventHandler> eventHandlers;


    @SuppressWarnings("unchecked")
    public VNode(Class<? extends Node> nodeClass,
                 Map<String, VProperty> properties,
                 Map<VEventType, VEventHandler> eventHandlers) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "Type must not be null");
        if (properties == null) {
            throw new NullPointerException("Properties must not be null");
        }
        this.children = properties.get("children")
                .map(property -> (Array<VNode>) property.getValue())
                .getOrElse(Array.empty());
        this.properties = properties.filter(entry -> ! "children".equals(entry._1));
        this.eventHandlers = Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");
    }


    public Class<? extends Node> getNodeClass() {
        return nodeClass;
    }

    public Array<VNode> getChildren() {
        return children;
    }

    public Map<String, VProperty> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandler> getEventHandlers() {
        return eventHandlers;
    }


    public int getSize() {
        return children.map(VNode::getSize).sum().intValue() + 1;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nodeClass", nodeClass)
                .append("children", children)
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }
}
