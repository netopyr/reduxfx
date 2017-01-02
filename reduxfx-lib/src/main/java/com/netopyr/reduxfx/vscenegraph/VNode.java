package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.Function3;
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class VNode<ACTION> {

    private final Class<? extends Node> nodeClass;
    private final Map<String, VProperty> properties;
    private final Map<VEventType, VEventHandlerElement> eventHandlers;


    public VNode(Class<? extends Node> nodeClass,
                 Map<String, VProperty> properties,
                 Map<VEventType, VEventHandlerElement> eventHandlers) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "Type must not be null");
        this.properties = properties == null ? HashMap.empty() : properties;
        this.eventHandlers = eventHandlers == null ? HashMap.empty() : eventHandlers;
    }


    public Class<? extends Node> getNodeClass() {
        return nodeClass;
    }

    public Map<String, VProperty> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandlerElement> getEventHandlers() {
        return eventHandlers;
    }


    @SuppressWarnings("unchecked")
    public Array<VNode<ACTION>> getChildren() {
        return properties.get("children").map(vProperty -> (Array<VNode<ACTION>>) vProperty.getValue()).getOrElse(Array.empty());
    }

    public int getSize() {
        return getChildren().map(VNode::getSize).sum().intValue() + 1;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nodeClass", nodeClass)
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }

    protected <CLASS extends VNode<ACTION>> CLASS addProperty(
            Function3<
                    Class<? extends Node>,
                    Map<String, VProperty>,
                    Map<VEventType, VEventHandlerElement>,
                    CLASS> creator,
            String name,
            Object value) {
        return creator.apply(
                getNodeClass(),
                getProperties().put(name, new VProperty<>(name, value)),
                getEventHandlers());
    }
}
