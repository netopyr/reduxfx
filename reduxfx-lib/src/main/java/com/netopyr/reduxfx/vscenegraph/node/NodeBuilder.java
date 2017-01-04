package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NodeBuilder<CLASS extends NodeBuilder<CLASS>> extends VNode {

    public NodeBuilder(Class<? extends Node> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new NodeBuilder(nodeClass, properties, eventHandlers);
    }


    protected CLASS addProperty(String name, Object value) {
        return create(
                getNodeClass(),
                getProperties().append(new VProperty<>(name, value, Option.none(), Option.none())),
                getEventHandlers()
        );
    }
    protected <TYPE> CLASS addProperty(String name, TYPE value, VChangeListener<TYPE> changeListener) {
        return create(
                getNodeClass(),
                getProperties().append(new VProperty<>(name, value, Option.of(changeListener), Option.none())),
                getEventHandlers()
        );
    }
    protected <TYPE> CLASS addProperty(String name, TYPE value, VInvalidationListener invalidationListener) {
        return create(
                getNodeClass(),
                getProperties().append(new VProperty<>(name, value, Option.none(), Option.of(invalidationListener))),
                getEventHandlers()
        );
    }
    protected <TYPE> CLASS addProperty(String name, TYPE value, VChangeListener<TYPE> changeListener, VInvalidationListener invalidationListener) {
        return create(
                getNodeClass(),
                getProperties().append(new VProperty<>(name, value, Option.of(changeListener), Option.of(invalidationListener))),
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
