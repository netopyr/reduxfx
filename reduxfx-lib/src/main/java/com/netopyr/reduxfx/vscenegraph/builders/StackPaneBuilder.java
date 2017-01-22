package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StackPaneBuilder<BUILDER extends StackPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String ALIGNMENT = "alignment";

    public StackPaneBuilder(Class<? extends Node> nodeClass,
                            Array<VProperty<?>> properties,
                            Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new StackPaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER alignment(Pos value) {
        return property(ALIGNMENT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
