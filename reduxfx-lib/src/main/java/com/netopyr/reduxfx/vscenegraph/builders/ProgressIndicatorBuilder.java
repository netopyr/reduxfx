package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProgressIndicatorBuilder<CLASS extends ProgressIndicatorBuilder<CLASS>> extends ControlBuilder<CLASS> {

    private static final String PROGRESS = "progress";

    public ProgressIndicatorBuilder(Class<? extends Node> nodeClass,
                                    Array<VProperty<?>> properties,
                                    Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ProgressIndicatorBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS progress(double value) {
        return property(PROGRESS, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
