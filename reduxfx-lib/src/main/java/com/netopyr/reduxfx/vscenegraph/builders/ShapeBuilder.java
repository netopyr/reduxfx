package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShapeBuilder<BUILDER extends ShapeBuilder<BUILDER>> extends NodeBuilder<BUILDER> {

    private static final String FILL = "fill";

    public ShapeBuilder(Class<? extends Node> nodeClass,
                        Array<VProperty<?>> properties,
                        Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new ShapeBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER fill(Paint value) {
        return property(FILL, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
