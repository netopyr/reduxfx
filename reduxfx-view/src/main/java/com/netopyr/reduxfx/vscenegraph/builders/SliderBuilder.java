package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"unused", "SameParameterValue"})
public class SliderBuilder<B extends SliderBuilder<B>> extends NodeBuilder<B> {

    private static final String MAX = "max";
    private static final String VALUE = "value";

    public SliderBuilder(Class<?> nodeClass,
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
        return (B) new SliderBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B max(double value) {
        return property(MAX, value);
    }
    public B max(double value, VChangeListener<Double> changeListener) {
        return property(MAX, value, changeListener);
    }
    public B max(double value, VInvalidationListener invalidationListener) {
        return property(MAX, value, invalidationListener);
    }
    public B max(double value, VChangeListener<Double> changeListener, VInvalidationListener invalidationListener) {
        return property(MAX, value, changeListener, invalidationListener);
    }

    public B value(double value) {
        return property(VALUE, value);
    }
    public B value(double value, VChangeListener<Double> changeListener) {
        return property(VALUE, value, changeListener);
    }
    public B value(double value, VInvalidationListener invalidationListener) {
        return property(VALUE, value, invalidationListener);
    }
    public B value(double value, VChangeListener<Double> changeListener, VInvalidationListener invalidationListener) {
        return property(VALUE, value, changeListener, invalidationListener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
