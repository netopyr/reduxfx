package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.ToggleGroupAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("SameParameterValue")
public class ToggleButtonBuilder<BUILDER extends ToggleButtonBuilder<BUILDER>> extends ButtonBaseBuilder<BUILDER> {

    private static final String SELECTED = "selected";
    private static final String TOGGLE_GROUP = "toggleGroup";

    public ToggleButtonBuilder(Class<?> nodeClass,
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
        return (BUILDER) new ToggleButtonBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public BUILDER selected(boolean value) {
        return property(SELECTED, value);
    }

    public BUILDER toggleGroup(String value) {
        Accessors.registerAccessor(getNodeClass(), "toggleGroup", ToggleGroupAccessor::new);
        return property(TOGGLE_GROUP, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
