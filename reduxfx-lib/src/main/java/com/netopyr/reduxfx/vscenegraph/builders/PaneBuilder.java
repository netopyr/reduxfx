package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaneBuilder<BUILDER extends PaneBuilder<BUILDER>> extends RegionBuilder<BUILDER> {

    private static final String CHILDREN = "children";

    public PaneBuilder(Class<?> nodeClass,
                       Map<String, VProperty> properties,
                       Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new PaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }

    public final BUILDER children(VNode... nodes) {
        return property(CHILDREN, nodes == null? Array.empty() : Array.of(nodes));
    }
    public final BUILDER children(Iterable<VNode> nodes) {
        return property(CHILDREN, nodes == null? Array.empty() : Array.ofAll(nodes));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
