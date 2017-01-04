package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class VNode {

    private final Class<? extends Node> nodeClass;
    private final Array<VNode> children;
    private final Array<VProperty<?>> properties;
    private final Array<VEventHandlerElement<?>> eventHandlers;


    @SuppressWarnings("unchecked")
    public VNode(Class<? extends Node> nodeClass,
                 Array<VProperty<?>> properties,
                 Array<VEventHandlerElement<?>> eventHandlers) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "Type must not be null");
        if (properties == null) {
            throw new NullPointerException("Properties must not be null");
        }
        this.children = properties.find(property -> "children".equals(property.getName()))
                .map(property -> (Array<VNode>) property.getValue())
                .getOrElse(Array.empty());
        this.properties = properties.filter(property -> ! "children".equals(property.getName()));
        this.eventHandlers = Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");
    }


    public Class<? extends Node> getNodeClass() {
        return nodeClass;
    }

    public Array<VNode> getChildren() {
        return children;
    }

    public Array<VProperty<?>> getProperties() {
        return properties;
    }

    public Array<VEventHandlerElement<?>> getEventHandlers() {
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
