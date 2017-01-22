package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VBoxBuilder<BUILDER extends VBoxBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String ALIGNMENT = "alignment";
    private static final String SPACING = "spacing";

    public VBoxBuilder(Class<? extends Node> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new VBoxBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER alignment(Pos value) {
        return property(ALIGNMENT, value);
    }

    public BUILDER spacing(double value) {
        return property(SPACING, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
