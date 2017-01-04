package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VBoxBuilder<CLASS extends VBoxBuilder<CLASS>> extends PaneBuilder<CLASS> {

    public VBoxBuilder(Class<? extends VBox> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new VBoxBuilder<>((Class<? extends VBox>) nodeClass, properties, eventHandlers);
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
