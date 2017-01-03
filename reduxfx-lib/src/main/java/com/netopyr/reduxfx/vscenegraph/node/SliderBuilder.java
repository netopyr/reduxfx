package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SliderBuilder<CLASS extends SliderBuilder<CLASS, ACTION>, ACTION> extends NodeBuilder<CLASS, ACTION> {

    public SliderBuilder(Class<? extends Node> nodeClass,
                         Map<String, VProperty> properties,
                         Map<VEventType, VEventHandlerElement> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    protected CLASS create(Class<? extends Node> nodeClass, Map<String, VProperty> properties, Map<VEventType, VEventHandlerElement> eventHandlers) {
        return (CLASS) new SliderBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS max(double value) {
        return this.addProperty("max", value);
    }
    public CLASS max(double value, VChangeListener<Double, ACTION> changeListener) {
        return this.addProperty("max", value, changeListener);
    }
    public CLASS max(double value, VInvalidationListener<ACTION> invalidationListener) {
        return this.addProperty("max", value, invalidationListener);
    }
    public CLASS max(double value, VChangeListener<Double, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
        return this.addProperty("max", value, changeListener, invalidationListener);
    }

    public CLASS value(double value) {
        return this.addProperty("value", value);
    }
    public CLASS value(double value, VChangeListener<Double, ACTION> changeListener) {
        return this.addProperty("value", value, changeListener);
    }
    public CLASS value(double value, VInvalidationListener<ACTION> invalidationListener) {
        return this.addProperty("value", value, invalidationListener);
    }
    public CLASS value(double value, VChangeListener<Double, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
        return this.addProperty("value", value, changeListener, invalidationListener);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
