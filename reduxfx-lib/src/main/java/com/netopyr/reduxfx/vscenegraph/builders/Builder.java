package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.impl.patcher.NodeUtilities;
import com.netopyr.reduxfx.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.impl.patcher.property.NodeListAccessor;
import com.netopyr.reduxfx.impl.patcher.property.SimpleNodeAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.Event;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("WeakerAccess")
public class Builder<BUILDER extends Builder<BUILDER>> extends VNode {

    public Builder(Class<?> nodeClass,
                   Map<String, Array<VNode>> childrenMap,
                   Map<String, Option<VNode>> singleChildMap,
                   Map<String, VProperty> properties,
                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected BUILDER create(Map<String, Array<VNode>> childrenMap,
                             Map<String, Option<VNode>> singleChildMap,
                             Map<String, VProperty> properties,
                             Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new Builder(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public BUILDER children(String name, Array<VNode> children) {
        Accessors.registerNodeListAccessors(getNodeClass(), name, () -> new NodeListAccessor(NodeUtilities.getGetterMethodHandle(getNodeClass(), name).get()));
        return Factory.node(
                this,
                getChildrenMap().put(name, children),
                getSingleChildMap(),
                getProperties(),
                getEventHandlers()
        );
    }

    public BUILDER child(String name, VNode child) {
        Accessors.registerNodeAccessors(getNodeClass(), name, () -> new SimpleNodeAccessor(NodeUtilities.getSetter(getNodeClass(), name).get()));
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap().put(name, Option.of(child)),
                getProperties(),
                getEventHandlers()
        );
    }

    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(value, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }

    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(value, changeListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, Object value, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(value, invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, Object value) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(value)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(changeListener, invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VChangeListener<?> changeListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(changeListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(invalidationListener)),
                getEventHandlers()
        );
    }

    public BUILDER property(String name) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property()),
                getEventHandlers()
        );
    }

    public <EVENT extends Event> BUILDER onEvent(VEventType type, VEventHandler<EVENT> eventHandler) {
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
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
