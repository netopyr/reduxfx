package com.netopyr.reduxfx.vscenegraph.impl.patcher;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessor;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.NodeAccessor;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.NodeListAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("WeakerAccess")
public class NodeBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(NodeBuilder.class);

    private static final String DISPATCHER_MUST_NOT_BE_NULL = "Dispatcher must not be null";
    private static final String NODE_MUST_NOT_BE_NULL = "Node must not be null";
    private static final String VNODE_MUST_NOT_BE_NULL = "VNode must not be null";
    private static final String PARENT_MUST_NOT_BE_NULL = "Parent must not be null";
    private static final String NAME_MUST_NOT_BE_NULL = "Name must not be null";

    private NodeBuilder() {}

    @SuppressWarnings("unchecked")
    public static Option<Object> create(VNode vNode) {
        Objects.requireNonNull(vNode, VNODE_MUST_NOT_BE_NULL);
        return vNode.produce();
    }

    @SuppressWarnings("unchecked")
    public static void init(Consumer<Object> dispatcher, Object node, VNode vNode) {
        Objects.requireNonNull(dispatcher, DISPATCHER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(node, NODE_MUST_NOT_BE_NULL);
        Objects.requireNonNull(vNode, VNODE_MUST_NOT_BE_NULL);

        updateProperties(dispatcher, node, vNode.getProperties());
        updateEventHandlers(dispatcher, node, vNode.getEventHandlers().map((name, eventHandler) -> Tuple.of(name, Option.of(eventHandler))));

        vNode.getSingleChildMap().forEach(tuple -> setSingleChild(dispatcher, node, tuple._1, tuple._2));
        vNode.getChildrenMap().forEach(tuple -> setChildren(dispatcher, node, tuple._1, tuple._2));
    }

    public static void setSingleChild(Consumer<Object> dispatcher, Object parent, String name, Option<VNode> child) {
        Objects.requireNonNull(dispatcher, DISPATCHER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(parent, PARENT_MUST_NOT_BE_NULL);
        Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);
        Objects.requireNonNull(child, "Child must not be null");

        final Option<NodeAccessor> accessor = Accessors.getNodeAccessor(parent, name);
        if (accessor.isDefined()) {
            accessor.get().set(dispatcher, parent, name, child);
        } else {
            LOG.warn("Accessor not found for child {} in class {}", name, parent.getClass());
        }
    }

    public static void setChildren(Consumer<Object> dispatcher, Object parent, String name, Array<VNode> children) {
        Objects.requireNonNull(dispatcher, DISPATCHER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(parent, PARENT_MUST_NOT_BE_NULL);
        Objects.requireNonNull(name, NAME_MUST_NOT_BE_NULL);
        Objects.requireNonNull(children, "Children must not be null");

        final Option<NodeListAccessor> accessor = Accessors.getNodeListAccessor(parent, name);
        if (accessor.isDefined()) {
            accessor.get().set(dispatcher, parent, name, children);
        } else {
            LOG.warn("Accessor not found for children {} in class {}", name, parent.getClass());
        }
    }

    @SuppressWarnings("unchecked")
    public static void updateProperties(Consumer<Object> dispatcher, Object node, Map<String, VProperty> properties) {
        Objects.requireNonNull(dispatcher, DISPATCHER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(node, NODE_MUST_NOT_BE_NULL);
        Objects.requireNonNull(properties, "Properties must not be null");

        for (final Tuple2<String, VProperty> entry : properties) {
            final Option<Accessor> accessor = Accessors.getAccessor(node, entry._1);
            if (accessor.isDefined()) {
                accessor.get().set(dispatcher, node, entry._1, entry._2);
            } else {
                LOG.warn("Accessor not found for property {} in class {}", entry._1, node.getClass());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void updateEventHandlers(Consumer<Object> dispatcher, Object node, Map<VEventType, Option<VEventHandler>> eventHandlers) {
        Objects.requireNonNull(dispatcher, DISPATCHER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(node, NODE_MUST_NOT_BE_NULL);
        Objects.requireNonNull(eventHandlers, "EventHandlers must not be null");

        for (final Tuple2<VEventType, Option<VEventHandler>> entry : eventHandlers) {
            final Option<MethodHandle> setter = getEventSetter(node.getClass(), entry._1);
            if (setter.isDefined()) {
                try {
                    final EventHandler<Event> eventHandler =
                            entry._2.map(
                                    vEventHandler ->
                                            (EventHandler<Event>) event -> {
                                                final Object action = vEventHandler.onChange(event);
                                                if (action != null) {
                                                    dispatcher.accept(action);
                                                }
                                            }
                            ).getOrElse((EventHandler) null);
                    setter.get().invoke(node, eventHandler);
                } catch (Exception e) {
                    LOG.error("Unable to set JavaFX EventHandler " + entry._1() + " for class " + node.getClass(), e);
                } catch (Throwable throwable) {
                    throw new Error("Unexpected error occurred", throwable);
                }
            }
        }
    }

    private static Option<MethodHandle> getEventSetter(Class<?> clazz, VEventType eventType) {
        final String eventName = eventType.getName();
        final String setterName = "setOn" + eventName.substring(0, 1).toUpperCase() + eventName.substring(1);

        Method method = null;
        try {
            method = clazz.getMethod(setterName, EventHandler.class);
        } catch (NoSuchMethodException e) {
            // ignore
        }

        if (method == null) {
            LOG.error("Unable to find setter for EventHandler {} in class {}", eventName, clazz);
            return Option.none();
        }

        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(method);
            return Option.of(methodHandle);
        } catch (IllegalAccessException e) {
            LOG.error("Setter for EventHandler {} in class {} is not accessible", eventName, clazz);
            return Option.none();
        }
    }
}
