package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProgressIndicatorBuilder<BUILDER extends ProgressIndicatorBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

    private static final String PROGRESS = "progress";

    public ProgressIndicatorBuilder(Class<?> nodeClass,
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
        return (BUILDER) new ProgressIndicatorBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
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
