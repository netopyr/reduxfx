package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SliderBuilder<BUILDER extends SliderBuilder<BUILDER>> extends NodeBuilder<BUILDER> {

    private static final String MAX = "max";
    private static final String VALUE = "value";

    public SliderBuilder(Class<?> nodeClass,
                         Map<String, VProperty> properties,
                         Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new SliderBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER max(double value) {
        return property(MAX, value);
    }
    public BUILDER max(double value, VChangeListener<Double> changeListener) {
        return property(MAX, value, changeListener);
    }
    public BUILDER max(double value, VInvalidationListener invalidationListener) {
        return property(MAX, value, invalidationListener);
    }
    public BUILDER max(double value, VChangeListener<Double> changeListener, VInvalidationListener invalidationListener) {
        return property(MAX, value, changeListener, invalidationListener);
    }

    public BUILDER value(double value) {
        return property(VALUE, value);
    }
    public BUILDER value(double value, VChangeListener<Double> changeListener) {
        return property(VALUE, value, changeListener);
    }
    public BUILDER value(double value, VInvalidationListener invalidationListener) {
        return property(VALUE, value, invalidationListener);
    }
    public BUILDER value(double value, VChangeListener<Double> changeListener, VInvalidationListener invalidationListener) {
        return property(VALUE, value, changeListener, invalidationListener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
