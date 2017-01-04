package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaneBuilder<CLASS extends PaneBuilder<CLASS>> extends RegionBuilder<CLASS> {

    public PaneBuilder(Class<? extends Pane> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new PaneBuilder<>((Class<? extends Pane>) nodeClass, properties, eventHandlers);
    }

    public final CLASS children(VNode... nodes) {
        return property("children", nodes == null? Array.empty() : Array.of(nodes));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
