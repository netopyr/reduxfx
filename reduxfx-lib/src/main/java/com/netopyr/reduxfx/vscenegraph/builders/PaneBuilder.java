package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaneBuilder<BUILDER extends PaneBuilder<BUILDER>> extends RegionBuilder<BUILDER> {

    private static final String CHILDREN = "children";

    public PaneBuilder(Class<? extends Node> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new PaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }

    public final BUILDER children(VNode... nodes) {
        return property(CHILDREN, nodes == null? Array.empty() : Array.of(nodes));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
