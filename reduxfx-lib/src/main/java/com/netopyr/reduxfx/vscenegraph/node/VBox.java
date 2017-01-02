package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;

public class VBox<ACTION> extends VNode<ACTION> {

    public VBox(Class<? extends Node> nodeClass,
                 Map<String, VProperty> properties,
                 Map<VEventType, VEventHandlerElement> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    public VBox<ACTION> spacing(double value) {
        return this.<VBox<ACTION>>addProperty(VBox::new, "spacing", value);
    }
}
