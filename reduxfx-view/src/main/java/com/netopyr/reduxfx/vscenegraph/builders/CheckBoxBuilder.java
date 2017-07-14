package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CheckBoxBuilder<B extends CheckBoxBuilder<B>> extends ButtonBaseBuilder<B> {

    private static final String SELECTED = "selected";

    public CheckBoxBuilder(Class<?> nodeClass,
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
        return (B) new CheckBoxBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B selected(boolean value, VChangeListener<Boolean> changeListener) {
        return property(SELECTED, value, changeListener);
    }
    public B selected(boolean value) {
        return property(SELECTED, value);
    }
    public B selected(VChangeListener<Boolean> changeListener) {
        return property(SELECTED, changeListener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
