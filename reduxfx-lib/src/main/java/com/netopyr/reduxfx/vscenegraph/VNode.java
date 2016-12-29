package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class VNode<ACTION> {

    private final Class<? extends Node> nodeClass;
    private final Array<VNode<ACTION>> children;
    private final Map<String, VProperty<?, ACTION>> properties;
    private final Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers;
    private final int size;


    public VNode(Class<? extends Node> nodeClass,
                 Array<VNode<ACTION>> children,
                 Map<String, VProperty<?, ACTION>> properties,
                 Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "Type must not be null");
        this.children = children == null? Array.empty() : children;
        this.properties = properties == null? HashMap.empty() : properties;
        this.eventHandlers = eventHandlers == null? HashMap.empty() : eventHandlers;

        this.size = children.map(VNode::getSize).sum().intValue() + 1;
    }


    public int getSize() {
        return size;
    }

    public Class<? extends Node> getNodeClass() {
        return nodeClass;
    }

    public Array<VNode<ACTION>> getChildren() {
        return children;
    }

    public Map<String, VProperty<?, ACTION>> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandlerElement<?, ACTION>> getEventHandlers() {
        return eventHandlers;
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
