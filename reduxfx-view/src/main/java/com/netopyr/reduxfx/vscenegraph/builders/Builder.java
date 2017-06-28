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
        Accessors.registerNodeListAccessor(getNodeClass(), name, () -> new DefaultNodeListAccessor(NodeUtilities.getGetterMethodHandle(getNodeClass(), name).get()));
        return Factory.node(
                this,
                getChildrenMap().put(name, children),
                getSingleChildMap(),
                getProperties(),
                getEventHandlers()
        );
    }

    public BUILDER child(String name, VNode child) {
        Accessors.registerNodeAccessor(getNodeClass(), name, () -> new DefaultNodeAccessor(NodeUtilities.getSetter(getNodeClass(), name).get()));
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap().put(name, Option.of(child)),
                getProperties(),
                getEventHandlers()
        );
    }

    public <TYPE> BUILDER property(Phase phase, String name, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }
    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, value, changeListener, invalidationListener);
    }

    public <TYPE> BUILDER property(Phase phase, String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value, changeListener)),
                getEventHandlers()
        );
    }
    public <TYPE> BUILDER property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return property(Phase.DEFAULT, name, value, changeListener);
    }

    public BUILDER property(Phase phase, String name, Object value, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value, invalidationListener)),
                getEventHandlers()
        );
    }
    public <TYPE> BUILDER property(String name, TYPE value, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, value, invalidationListener);
    }

    public BUILDER property(Phase phase, String name, Object value) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, value)),
                getEventHandlers()
        );
    }
    public BUILDER property(String name, Object value) {
        return property(Phase.DEFAULT, name, value);
    }

    public BUILDER property(Phase phase, String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, changeListener, invalidationListener)),
                getEventHandlers()
        );
    }
    public BUILDER property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, changeListener, invalidationListener);
    }

    public BUILDER property(Phase phase, String name, VChangeListener<?> changeListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, changeListener)),
                getEventHandlers()
        );
    }
    public BUILDER property(String name, VChangeListener<?> changeListener) {
        return property(Phase.DEFAULT, name, changeListener);
    }

    public BUILDER property(Phase phase, String name, VInvalidationListener invalidationListener) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase, invalidationListener)),
                getEventHandlers()
        );
    }
    public BUILDER property(String name, VInvalidationListener invalidationListener) {
        return property(Phase.DEFAULT, name, invalidationListener);
    }

    public BUILDER property(Phase phase, String name) {
        return Factory.node(
                this,
                getChildrenMap(),
                getSingleChildMap(),
                getProperties().put(name, Factory.property(phase)),
                getEventHandlers()
        );
    }
    public BUILDER property(String name) {
        return property(Phase.DEFAULT, name);
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
