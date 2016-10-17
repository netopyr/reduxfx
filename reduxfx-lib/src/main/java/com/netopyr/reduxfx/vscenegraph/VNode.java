package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VNode<ACTION> implements VElement<ACTION> {

    private final Class<? extends Node> nodeClass;
    private final Array<VNode<ACTION>> children;
    private final Map<String, VProperty<?, ACTION>> properties;
    private final Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers;
    private final int size;


    @SafeVarargs
    public VNode(Class<? extends Node> nodeClass, VElement<ACTION>... elements) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "Type must not be null");

        final Array<VElement<ACTION>> allElements = elements != null? Array.of(elements) : Array.empty();

        this.children = allElements.filter(element -> element instanceof VNode)
                .map(element -> (VNode<ACTION>) element);
        this.properties = allElements.filter(element -> element instanceof VProperty)
                .map(element -> (VProperty<?, ACTION>) element)
                .toMap(element -> new Tuple2<>(element.getName(), element));
        this.eventHandlers = allElements.filter(element -> element instanceof VEventHandlerElement)
                .map(element -> (VEventHandlerElement<?, ACTION>) element)
                .toMap(element -> new Tuple2<>(element.getType(), element));

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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VNode<?> vNode = (VNode<?>) o;

        return new EqualsBuilder()
                .append(nodeClass, vNode.nodeClass)
                .append(children, vNode.children)
                .append(properties, vNode.properties)
                .append(eventHandlers, vNode.eventHandlers)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(nodeClass)
                .append(children)
                .append(properties)
                .append(eventHandlers)
                .toHashCode();
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
