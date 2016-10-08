package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VNode<ACTION> implements VElement<ACTION> {

    private final VNodeType type;
    private final Array<VNode<ACTION>> children;
    private final Map<VPropertyType, VProperty<?, ACTION>> properties;
    private final Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers;
    private final int size;


    @SafeVarargs
    public VNode(VNodeType type, VElement<ACTION>... elements) {
        this.type = Objects.requireNonNull(type, "Type must not be null");

        final Array<VElement<ACTION>> allElements = elements != null? Array.of(elements) : Array.empty();

        this.children = allElements.filter(element -> element instanceof VNode)
                .map(element -> (VNode<ACTION>) element);
        this.properties = allElements.filter(element -> element instanceof VProperty)
                .map(element -> (VProperty<?, ACTION>) element)
                .toMap(element -> new Tuple2<>(element.getType(), element));
        this.eventHandlers = allElements.filter(element -> element instanceof VEventHandlerElement)
                .map(element -> (VEventHandlerElement<?, ACTION>) element)
                .toMap(element -> new Tuple2<>(element.getType(), element));

        this.size = children.map(VNode::getSize).sum().intValue() + 1;
    }


    public int getSize() {
        return size;
    }

    public VNodeType getType() {
        return type;
    }

    public Array<VNode<ACTION>> getChildren() {
        return children;
    }

    public Map<VPropertyType, VProperty<?, ACTION>> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandlerElement<?, ACTION>> getEventHandlers() {
        return eventHandlers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("children", children)
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }
}
