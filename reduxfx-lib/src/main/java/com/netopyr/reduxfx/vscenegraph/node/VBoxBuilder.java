package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VBoxBuilder<CLASS extends VBoxBuilder<CLASS, ACTION>, ACTION> extends NodeBuilder<CLASS, ACTION> {

    public VBoxBuilder(Class<? extends Node> nodeClass,
                       Map<String, VProperty> properties,
                       Map<VEventType, VEventHandlerElement> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Map<String, VProperty> properties, Map<VEventType, VEventHandlerElement> eventHandlers) {
        return (CLASS) new VBoxBuilder<>(nodeClass, properties, eventHandlers);
    }

    public CLASS spacing(double value) {
        return this.addProperty("spacing", value);
    }

    @SafeVarargs
    public final CLASS children(VNode<ACTION>... value) {
        return this.addProperty("children", value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
