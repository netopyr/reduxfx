package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class VNode {

    private static final Logger LOG = LoggerFactory.getLogger(VNode.class);

    private final Class<?> nodeClass;
    private final Map<String, Array<VNode>> childrenMap;
    private final Map<String, Option<VNode>> singleChildMap;
    private final Map<String, VProperty> properties;
    private final Map<VEventType, VEventHandler> eventHandlers;


    @SuppressWarnings("unchecked")
    public VNode(Class<?> nodeClass,
                 Map<String, Array<VNode>> children,
                 Map<String, Option<VNode>> singleChildMap,
                 Map<String, VProperty> properties,
                 Map<VEventType, VEventHandler> eventHandlers) {
        this.nodeClass = Objects.requireNonNull(nodeClass, "NodeClass must not be null");
        this.childrenMap = Objects.requireNonNull(children, "ChildrenMap must not be null");
        this.singleChildMap = Objects.requireNonNull(singleChildMap, "SingleChildMap must not be null");
        this.properties = Objects.requireNonNull(properties, "Properties must not be null");
        this.eventHandlers = Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");
    }


    public Option<Object> produce() {
        try {
            final Object node = nodeClass.newInstance();
            return Option.of(node);
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Unable to create node", e);
            return Option.none();
        }
    }


    public Class<?> getNodeClass() {
        return nodeClass;
    }

    public Map<String, Array<VNode>> getChildrenMap() {
        return childrenMap;
    }

    public Map<String, Option<VNode>> getSingleChildMap() {
        return singleChildMap;
    }

    public Map<String, VProperty> getProperties() {
        return properties;
    }

    public Map<VEventType, VEventHandler> getEventHandlers() {
        return eventHandlers;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("nodeClass", nodeClass)
                .append("childrenMap", childrenMap)
                .append("singleChildMap", singleChildMap)
                .append("properties", properties)
                .append("eventHandlers", eventHandlers)
                .toString();
    }
}
