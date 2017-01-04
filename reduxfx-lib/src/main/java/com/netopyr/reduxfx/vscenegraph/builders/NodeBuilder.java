package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Array;
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


    @SuppressWarnings("unchecked")
    protected <T> CLASS property(String name, T value, VChangeListener<? super T> changeListener, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value, changeListener, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected <TYPE> CLASS property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value, changeListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected CLASS property(String name, Object value, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected CLASS property(String name, Object value) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected CLASS property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, changeListener, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected CLASS property(String name, VChangeListener<?> changeListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, changeListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected CLASS property(String name, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    protected CLASS property(String name) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name)),
                getEventHandlers(),
                this::create
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
