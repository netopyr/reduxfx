package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RectangleBuilder<BUILDER extends RectangleBuilder<BUILDER>> extends ShapeBuilder<BUILDER> {

    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String X = "x";
    private static final String Y = "y";

    public RectangleBuilder(Class<?> nodeClass,
                            Array<VNode> children,
                            Map<String, VProperty> namedChildren,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new RectangleBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER height(double value) {
        return property(HEIGHT, value);
    }

    public BUILDER width(double value) {
        return property(WIDTH, value);
    }

    public BUILDER x(double value) {
        return property(X, value);
    }

    public BUILDER y(double value) {
        return property(Y, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
