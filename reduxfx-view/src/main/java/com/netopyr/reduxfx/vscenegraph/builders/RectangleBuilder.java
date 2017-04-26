package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("unused")
public class RectangleBuilder<BUILDER extends RectangleBuilder<BUILDER>> extends ShapeBuilder<BUILDER> {

    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String X = "x";
    private static final String Y = "y";

    public RectangleBuilder(Class<?> nodeClass,
                            Map<String, Array<VNode>> childrenMap,
                            Map<String, Option<VNode>> singleChildMap,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new RectangleBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
