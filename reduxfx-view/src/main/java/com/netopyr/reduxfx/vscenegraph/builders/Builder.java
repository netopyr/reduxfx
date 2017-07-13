package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.DefaultNodeAccessor;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.DefaultNodeListAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VProperty.Phase;
import javafx.event.Event;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("WeakerAccess")
public class Builder<B extends Builder<B>> extends VNode {

    public Builder(Class<?> nodeClass,
                   Map<String, Array<VNode>> childrenMap,
                   Map<String, Option<VNode>> singleChildMap,
                   Map<String, VProperty> properties,
                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected B create(Map<String, Array<VNode>> childrenMap,
                             Map<String, Option<VNode>> singleChildMap,
                             Map<String, VProperty> properties,
                             Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new Builder(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B children(String name, Array<VNode> children) {
        Accessors.registerNodeListAccessor(getNodeClass(), name, () -> new DefaultNodeListAccessor(NodeUtilities.getGetterMethodHandle(getNodeClass(), name).get()));
        return Factory.node(
                this,
                getChildrenMap().put(name, children),
                getSingleChildMap(),
                getProperties(),
                getEventHandlers()
        );
    }

    public B child(String name, VNode child) {
        Accessors.registerNodeAccessor(getNodeClass(), name, () -> new DefaultNodeAccessor(NodeUtilities.getSetter(getNodeClass(), name).get()));
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap().put(name, Option.of(child)),
                getProperties(),
                getEventHandlers()
        );
    }

    public <T> B property(Phase phase, String name, T value, VChangeListener<? super T> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }
    public <T> B property(String name, T value, VChangeListener<? super T> changeListener, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, value, changeListener, invalidationListener);
    }

    public <T> B property(Phase phase, String name, T value, VChangeListener<? super T> changeListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value, changeListener)),
                getEventHandlers()
        );
    }
    public <T> B property(String name, T value, VChangeListener<? super T> changeListener) {
        return property(Phase.DEFAULT, name, value, changeListener);
    }

    public B property(Phase phase, String name, Object value, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value, invalidationListener)),
                getEventHandlers()
        );
    }
    public <T> B property(String name, T value, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, value, invalidationListener);
    }

    public B property(Phase phase, String name, Object value) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value)),
                getEventHandlers()
        );
    }
    public B property(String name, Object value) {
        return property(Phase.DEFAULT, name, value);
    }

    public B property(Phase phase, String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }
    public B property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, changeListener, invalidationListener);
    }

    public B property(Phase phase, String name, VChangeListener<?> changeListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, changeListener)),
                getEventHandlers()
        );
    }
    public B property(String name, VChangeListener<?> changeListener) {
        return property(Phase.DEFAULT, name, changeListener);
    }

    public B property(Phase phase, String name, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, invalidationListener)),
                getEventHandlers()
        );
    }
    public B property(String name, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, invalidationListener);
    }

    public B property(Phase phase, String name) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase)),
                getEventHandlers()
        );
    }
    public B property(String name) {
        return property(Phase.DEFAULT, name);
    }

    public <E extends Event> B onEvent(VEventType type, VEventHandler<E> eventHandler) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties(),
                getEventHandlers().put(type, eventHandler)
        );
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
