package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SliderBuilder<CLASS extends SliderBuilder<CLASS>> extends NodeBuilder<CLASS> {

    public SliderBuilder(Class<? extends Node> nodeClass,
                         Array<VProperty<?>> properties,
                         Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new SliderBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS max(double value) {
        return property("max", value);
    }
    public CLASS max(double value, VChangeListener<Double> changeListener) {
        return property("max", value, changeListener);
    }
    public CLASS max(double value, VInvalidationListener invalidationListener) {
        return property("max", value, invalidationListener);
    }
    public CLASS max(double value, VChangeListener<Double> changeListener, VInvalidationListener invalidationListener) {
        return property("max", value, changeListener, invalidationListener);
    }

    public CLASS value(double value) {
        return property("value", value);
    }
    public CLASS value(double value, VChangeListener<Double> changeListener) {
        return property("value", value, changeListener);
    }
    public CLASS value(double value, VInvalidationListener invalidationListener) {
        return property("value", value, invalidationListener);
    }
    public CLASS value(double value, VChangeListener<Double> changeListener, VInvalidationListener invalidationListener) {
        return property("value", value, changeListener, invalidationListener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
