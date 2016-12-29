package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;

public class VBox<ACTION> extends VNode<ACTION> {

    public static VBox VBox() {
        return new VBox();
    }

    private VBox() {
        super(javafx.scene.layout.VBox.class, Array.empty());
    }
    private VBox(javafx.scene.layout.VBox nodeClass,
                 Array<VNode<ACTION>> children,
                 Map<String, VProperty<?, ACTION>> properties,
                 Map<VEventType, VEventHandlerElement<?, ACTION>> eventHandlers) {
        super(nodeClass, children, properties, eventHandlers)
    }
    private VBox(Array<>)

    public VBox spacing(double value) {
        return new VBox();
    }
}
