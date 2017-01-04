package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class HBoxBuilder<CLASS extends HBoxBuilder<CLASS>> extends PaneBuilder<CLASS> {

    public HBoxBuilder(Class<? extends HBox> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new HBoxBuilder<>((Class<? extends HBox>) nodeClass, properties, eventHandlers);
    }

    public CLASS spacing(double value) {
        return property("spacing", value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
