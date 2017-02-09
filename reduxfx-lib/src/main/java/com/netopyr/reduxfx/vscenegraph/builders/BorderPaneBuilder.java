package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BorderPaneBuilder<BUILDER extends BorderPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String BOTTOM = "bottom";
    private static final String CENTER = "center";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";

    public BorderPaneBuilder(Class<?> nodeClass,
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
        return (BUILDER) new BorderPaneBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER bottom(VNode value) {
        return child(BOTTOM, value);
    }

    public BUILDER center(VNode value) {
        return child(CENTER, value);
    }

    public BUILDER left(VNode value) {
        return child(LEFT, value);
    }

    public BUILDER right(VNode value) {
        return child(RIGHT, value);
    }

    public BUILDER top(VNode value) {
        return child(TOP, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
