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
public class BorderPaneBuilder<BUILDER extends BorderPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String BOTTOM = "bottom";
    private static final String CENTER = "center";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";

    public BorderPaneBuilder(Class<?> nodeClass,
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
        return (BUILDER) new BorderPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
