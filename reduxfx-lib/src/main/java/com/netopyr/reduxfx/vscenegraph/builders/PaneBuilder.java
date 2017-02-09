package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaneBuilder<BUILDER extends PaneBuilder<BUILDER>> extends RegionBuilder<BUILDER> {

    public PaneBuilder(Class<?> nodeClass,
                       Array<VNode> children,
                       Map<String, VProperty> namedChildren,
                       Map<String, VProperty> properties,
                       Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new PaneBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }

    public final BUILDER children(VNode... nodes) {
        return children(nodes == null? Array.empty() : Array.of(nodes));
    }
    public final BUILDER children(Iterable<VNode> nodes) {
        return children(nodes == null? Array.empty() : Array.ofAll(nodes));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
