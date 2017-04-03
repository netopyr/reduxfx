package com.netopyr.reduxfx.impl.patcher;

import com.netopyr.reduxfx.impl.patcher.property.Accessor;
import com.netopyr.reduxfx.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;

import static com.netopyr.reduxfx.impl.patcher.NodeUtilities.appendNode;

@SuppressWarnings("WeakerAccess")
public class NodeBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(NodeBuilder.class);

    private NodeBuilder() {}

    @SuppressWarnings("unchecked")
    public static Option<Object> create(VNode vNode) {
        Objects.requireNonNull(vNode, "VNode must not be null");
        try {
            final Class<?> nodeClass = vNode.getNodeClass();
            final Object node = nodeClass.newInstance();
            return Option.of(node);
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Unable to create node", e);
            return Option.none();
        }
    }

    @SuppressWarnings("unchecked")
    public static void init(Consumer<Object> dispatcher, Object node, VNode vNode) {
        Objects.requireNonNull(dispatcher, "Dispatcher must not be null");
        Objects.requireNonNull(node, "Node must not be null");
        Objects.requireNonNull(vNode, "VNode must not be null");

        updateProperties(dispatcher, node, vNode.getProperties());
        updateEventHandlers(dispatcher, node, vNode.getEventHandlers().map((name, eventHandler) -> Tuple.of(name, Option.of(eventHandler))));
        updateProperties(dispatcher, node, vNode.getNamedChildren());

        if (vNode.getChildren().nonEmpty()) {
            vNode.getChildren().forEach(vChild -> {
                final Option<Object> child = create(vChild);
                if (child.isDefined()) {
                    appendNode(node, child.get());
                    init(dispatcher, child.get(), vChild);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    public static void updateProperties(Consumer<Object> dispatcher, Object node, Map<String, VProperty> properties) {
        Objects.requireNonNull(dispatcher, "Dispatcher must not be null");
        Objects.requireNonNull(node, "Node must not be null");
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
        Objects.requireNonNull(dispatcher, "Dispatcher must not be null");
        Objects.requireNonNull(node, "Node must not be null");
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
                } catch (Throwable throwable) {
                    LOG.error("Unable to set JavaFX EventHandler " + entry._1() + " for class " + node.getClass(), throwable);
                }
            }
        }
    }

    private static Option<MethodHandle> getEventSetter(Class<?> clazz, VEventType eventType) {
        // TODO: Cache the getter
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
