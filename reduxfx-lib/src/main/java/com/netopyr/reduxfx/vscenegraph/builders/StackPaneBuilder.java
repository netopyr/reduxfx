package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class StackPaneBuilder<CLASS extends StackPaneBuilder<CLASS>> extends PaneBuilder<CLASS> {

    private static final String ALIGNMENT = "alignment";

    public StackPaneBuilder(Class<? extends Node> nodeClass,
                            Array<VProperty<?>> properties,
                            Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new StackPaneBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS alignment(Pos value) {
        return property(ALIGNMENT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
