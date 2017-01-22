package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProgressIndicatorBuilder<BUILDER extends ProgressIndicatorBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

    private static final String PROGRESS = "progress";

    public ProgressIndicatorBuilder(Class<? extends Node> nodeClass,
                                    Array<VProperty<?>> properties,
                                    Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new ProgressIndicatorBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER progress(double value) {
        return property(PROGRESS, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
