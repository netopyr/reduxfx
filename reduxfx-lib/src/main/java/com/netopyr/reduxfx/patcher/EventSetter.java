package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.function.Consumer;

class EventSetter<ACTION> {

    private static final Logger LOG = LoggerFactory.getLogger(EventSetter.class);

    private final Consumer<ACTION> dispatcher;

    EventSetter(Consumer<ACTION> dispatcher) {
        this.dispatcher = dispatcher;
    }

    @SuppressWarnings("unchecked")
    void setEventHandlers(Node node, Map<VEventType, VEventHandlerElement<? extends Event, ACTION>> eventHandlers) {
        for (final VEventHandlerElement eventHandlerElement : eventHandlers.values()) {
            final Option<MethodHandle> setter = getEventSetter(node.getClass(), eventHandlerElement.getType());
            if (setter.isDefined()) {
                try {
                    final EventHandler<? extends Event> eventHandler = e -> {
                        final ACTION action = (ACTION) eventHandlerElement.getEventHandler().onChange(e);
                        dispatcher.accept(action);
                    };
                    setter.get().invoke(node, eventHandler);
                } catch (Throwable throwable) {
                    LOG.error("Unable to set JavaFX EventHandler " + eventHandlerElement.getType() + " for class " + node.getClass(), throwable);
                }
            }
        }
    }

    private static Option<MethodHandle> getEventSetter(Class<? extends Node> clazz, VEventType eventType) {
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
