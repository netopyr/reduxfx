package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SceneBuilder<BUILDER extends SceneBuilder<BUILDER>> extends Builder<BUILDER> {

    private static final String ROOT = "root";

    public SceneBuilder(Class<?> nodeClass, Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new SceneBuilder<>(getNodeClass(), properties, eventHandlers);
    }

    public BUILDER root(VNode value) {
        return property(ROOT, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
