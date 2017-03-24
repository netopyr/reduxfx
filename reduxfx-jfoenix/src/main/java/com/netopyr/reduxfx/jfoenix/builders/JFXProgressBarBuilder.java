package com.netopyr.reduxfx.jfoenix.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.ProgressBarBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("unused")
public class JFXProgressBarBuilder<BUILDER extends JFXProgressBarBuilder<BUILDER>> extends ProgressBarBuilder<BUILDER> {

    public JFXProgressBarBuilder(Class<?> nodeClass,
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
        return (BUILDER) new JFXProgressBarBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
