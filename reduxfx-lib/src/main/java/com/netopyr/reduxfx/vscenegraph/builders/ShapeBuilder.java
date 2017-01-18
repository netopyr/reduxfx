package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShapeBuilder<CLASS extends ShapeBuilder<CLASS>> extends NodeBuilder<CLASS> {

    private static final String FILL = "fill";

    public ShapeBuilder(Class<? extends Node> nodeClass,
                        Array<VProperty<?>> properties,
                        Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ShapeBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS fill(Paint value) {
        return property(FILL, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
