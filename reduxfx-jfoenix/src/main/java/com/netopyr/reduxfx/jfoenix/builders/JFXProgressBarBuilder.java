package com.netopyr.reduxfx.jfoenix.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.ProgressBarBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("unused")
public class JFXProgressBarBuilder<BUILDER extends JFXProgressBarBuilder<BUILDER>> extends ProgressBarBuilder<BUILDER> {

    public JFXProgressBarBuilder(Class<?> nodeClass,
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
        return (BUILDER) new JFXProgressBarBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
