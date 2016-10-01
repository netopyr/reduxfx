package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.VEventType;
import com.netopyr.reduxfx.vscenegraph.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VProperty;
import com.netopyr.reduxfx.vscenegraph.VPropertyType;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Consumer;

class NodeUtilities {

    private static final Logger LOG = LoggerFactory.getLogger(NodeUtilities.class);



    static Option<java.util.List<Node>> getChildren(Node node) {
        return node instanceof Group ? Option.of(((Group) node).getChildren())
                : node instanceof Pane ? Option.of(((Pane) node).getChildren())
                : Option.none();
    }



    @SuppressWarnings("unchecked")
    static void setAllAttributes(Node node, VNode vNode, Consumer dispatcher) {
        setProperties(node, vNode.getProperties(), dispatcher);
        setEventHandlers(node, vNode.getEventHandlers(), dispatcher);
    }



    @SuppressWarnings("unchecked")
    static void setProperties(Node node, Map<VPropertyType, VProperty> properties, Consumer dispatcher) {
        for (final VProperty vProperty : properties.values()) {
            final Option<MethodHandle> propertyGetter = getPropertyGetter(node.getClass(), vProperty.getType());
            if (propertyGetter.isDefined()) {
                try {
                    final Property property = (Property<?>) propertyGetter.get().invoke(node);

                    setProperty(property, vProperty.getValue());

                    if (vProperty.getChangeListener().isDefined()) {
                        setChangeListener(node, property, (VChangeListener) vProperty.getChangeListener().get(), dispatcher);
                    }

                    if (vProperty.getInvalidationListener().isDefined()) {
                        setInvalidationListener(node, property, (VInvalidationListener) vProperty.getInvalidationListener().get(), dispatcher);
                    }
                } catch (Throwable throwable) {
                    LOG.error("Unable to read property " + vProperty.getType() + " from Node-class " + node.getClass(), throwable);
                }
            }
        }
    }

    private static Option<MethodHandle> getPropertyGetter(Class<? extends Node> clazz, VPropertyType propertyType) {
        // TODO: Cache the getter
        final String propertyName = propertyType.getName();
        final String getterName = propertyName + "Property";

        Method method = null;
        try {
            method = clazz.getMethod(getterName);
        } catch (NoSuchMethodException e) {
            // ignore
        }

        if (method == null) {
            LOG.error("Unable to find getter for Property {} in class {}", propertyName, clazz);
            return Option.none();
        }

        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(method);
            return Option.of(methodHandle);
        } catch (IllegalAccessException e) {
            LOG.error("Getter for Property {} in class {} is not accessible", propertyName, clazz);
            return Option.none();
        }
    }

    @SuppressWarnings("unchecked")
    private static void setProperty(Property property, Object newValue) {
        final Object oldValue = property.getValue();
        if (oldValue instanceof ObservableList) {
            if (newValue instanceof Seq) {
                ((ObservableList) oldValue).setAll(((Seq) newValue).toJavaList());
            } else if (newValue instanceof List) {
                ((ObservableList) oldValue).setAll((List) newValue);
            } else {
                property.setValue(newValue);
            }
        } else {
            property.setValue(newValue);
        }
    }

    @SuppressWarnings("unchecked")
    private static void setChangeListener(Node node, Property property, VChangeListener listener, Consumer dispatcher) {
        final ChangeListener oldListener = (ChangeListener) node.getProperties().get(property.getName() + ".change");
        if (oldListener != null) {
            property.removeListener(oldListener);
        }
        final ChangeListener newListener = (source, oldValue, newValue) -> {
            final Object action = listener.onChange(oldValue, newValue);
            dispatcher.accept(action);
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".change", newListener);
    }

    @SuppressWarnings("unchecked")
    private static void setInvalidationListener(Node node, Property property, VInvalidationListener listener, Consumer dispatcher) {
        final InvalidationListener oldListener = (InvalidationListener) node.getProperties().get(property.getName() + ".invalidation");
        if (oldListener != null) {
            property.removeListener(oldListener);
        }
        final InvalidationListener newListener = (source) -> {
            final Object action = listener.onInvalidation();
            dispatcher.accept(action);
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".invalidation", newListener);
    }



    @SuppressWarnings("unchecked")
    static void setEventHandlers(Node node, Map<VEventType, VEventHandlerElement> eventHandlers, Consumer dispatcher) {
        for (final VEventHandlerElement eventHandlerElement : eventHandlers.values()) {
            final Option<MethodHandle> setter = getEventSetter(node.getClass(), eventHandlerElement.getType());
            if (setter.isDefined()) {
                try {
                    final EventHandler eventHandler = e -> {
                        final Object action = eventHandlerElement.getEventHandler().onChange(e);
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
