package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.Event;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Builder<BUILDER extends Builder<BUILDER>> extends VNode {

    public Builder(Class<?> nodeClass,
                   Array<VNode> children,
                   Map<String, VProperty> namedChildren,
                   Map<String, VProperty> properties,
                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected BUILDER create(Array<VNode> children,
                             Map<String, VProperty> namedChildren,
                             Map<String, VProperty> properties,
                             Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new Builder(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER children(Array<VNode> children) {
        return Factory.node(
                this,
                children,
                getNamedChildren(),
                getProperties(),
                getEventHandlers()
        );
    }

    public <TYPE extends VNode> BUILDER child(String name, TYPE child, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren().put(name, Factory.property(child, changeListener, invalidationListener)),
                getProperties(),
                getEventHandlers()
        );
    }

    public <TYPE extends VNode> BUILDER child(String name, TYPE child, VChangeListener<? super TYPE> changeListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren().put(name, Factory.property(child, changeListener)),
                getProperties(),
                getEventHandlers()
        );
    }

    public <TYPE extends VNode> BUILDER child(String name, TYPE child, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren().put(name, Factory.property(child, invalidationListener)),
                getProperties(),
                getEventHandlers()
        );
    }

    public BUILDER child(String name, VNode child) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren().put(name, Factory.property(child)),
                getProperties(),
                getEventHandlers()
        );
    }

    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(value, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }

    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(value, changeListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, Object value, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(value, invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, Object value) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(value)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(changeListener, invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VChangeListener<?> changeListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(changeListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property(invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties().put(name, Factory.property()),
                getEventHandlers()
        );
    }

    public <EVENT extends Event> BUILDER onEvent(VEventType type, VEventHandler<EVENT> eventHandler) {
        return Factory.node(
                this,
                getChildren(),
                getNamedChildren(),
                getProperties(),
                getEventHandlers().put(type, eventHandler)
        );
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
