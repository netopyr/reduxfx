package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ScrollPaneBuilder<BUILDER extends ScrollPaneBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

    private static final String CONTENT = "content";

    public ScrollPaneBuilder(Class<?> nodeClass,
                             Map<String, VProperty> properties,
                             Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ScrollPaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER content(VNode value) {
        return property(CONTENT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
