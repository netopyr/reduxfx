package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.Event;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Builder<BUILDER extends Builder<BUILDER>> extends VNode {

    public Builder(Class<?> nodeClass,
                   Map<String, VProperty> properties,
                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new Builder(getNodeClass(), properties, eventHandlers);
    }


    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(value, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }

    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(value, changeListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, Object value, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(value, invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, Object value) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(value)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(changeListener, invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VChangeListener<?> changeListener) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(changeListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property(invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name) {
        return Factory.node(
                this,
                getProperties().put(name, Factory.property()),
                getEventHandlers()
        );
    }

    public <EVENT extends Event> BUILDER onEvent(VEventType type, VEventHandler<EVENT> eventHandler) {
        return Factory.node(
                this,
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
