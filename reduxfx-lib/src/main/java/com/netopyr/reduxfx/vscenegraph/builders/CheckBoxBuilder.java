package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CheckBoxBuilder<BUILDER extends CheckBoxBuilder<BUILDER>> extends ButtonBaseBuilder<BUILDER> {

    private static final String SELECTED = "selected";

    public CheckBoxBuilder(Class<?> nodeClass,
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
        return (BUILDER) new CheckBoxBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER selected(boolean value, VChangeListener<Boolean> changeListener) {
        return property(SELECTED, value, changeListener);
    }
    public BUILDER selected(boolean value) {
        return property(SELECTED, value);
    }
    public BUILDER selected(VChangeListener<Boolean> changeListener) {
        return property(SELECTED, changeListener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
