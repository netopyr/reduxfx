package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NodeBuilder<CLASS extends NodeBuilder<CLASS, ACTION>, ACTION> extends VNode<ACTION> {

    public NodeBuilder(Class<? extends Node> nodeClass,
                       Map<String, VProperty> properties,
                       Map<VEventType, VEventHandlerElement> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected CLASS create(Class<? extends Node> nodeClass, Map<String, VProperty> properties, Map<VEventType, VEventHandlerElement> eventHandlers) {
        return (CLASS) new NodeBuilder<>(nodeClass, properties, eventHandlers);
    }

    public VNode<ACTION> build() {
        return this;
    }


    protected CLASS addProperty(String name, Object value) {
        return create(
                getNodeClass(),
                getProperties().put(name, new VProperty<>(name, value, Option.none(), Option.none())),
                getEventHandlers()
        );
    }
    protected <TYPE> CLASS addProperty(String name, TYPE value, VChangeListener<TYPE, ACTION> changeListener) {
        return create(
                getNodeClass(),
                getProperties().put(name, new VProperty<>(name, value, Option.of(changeListener), Option.none())),
                getEventHandlers()
        );
    }
    protected <TYPE> CLASS addProperty(String name, TYPE value, VInvalidationListener<ACTION> invalidationListener) {
        return create(
                getNodeClass(),
                getProperties().put(name, new VProperty<>(name, value, Option.none(), Option.of(invalidationListener))),
                getEventHandlers()
        );
    }
    protected <TYPE> CLASS addProperty(String name, TYPE value, VChangeListener<TYPE, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
        return create(
                getNodeClass(),
                getProperties().put(name, new VProperty<>(name, value, Option.of(changeListener), Option.of(invalidationListener))),
                getEventHandlers()
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
