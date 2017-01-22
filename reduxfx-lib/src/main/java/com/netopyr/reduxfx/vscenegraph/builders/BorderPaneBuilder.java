package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BorderPaneBuilder<BUILDER extends BorderPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String BOTTOM = "bottom";
    private static final String CENTER = "center";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";

    public BorderPaneBuilder(Class<? extends Node> nodeClass,
                             Array<VProperty<?>> properties,
                             Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new BorderPaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER bottom(VNode value) {
        return property(BOTTOM, value);
    }

    public BUILDER center(VNode value) {
        return property(CENTER, value);
    }

    public BUILDER left(VNode value) {
        return property(LEFT, value);
    }

    public BUILDER right(VNode value) {
        return property(RIGHT, value);
    }

    public BUILDER top(VNode value) {
        return property(TOP, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
