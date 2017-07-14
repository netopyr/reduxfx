package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StackPaneBuilder<B extends StackPaneBuilder<B>> extends PaneBuilder<B> {

    private static final String ALIGNMENT = "alignment";

    public StackPaneBuilder(Class<?> nodeClass,
                            Map<String, Array<VNode>> childrenMap,
                            Map<String, Option<VNode>> singleChildMap,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new StackPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B alignment(Pos value) {
        return property(ALIGNMENT, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
