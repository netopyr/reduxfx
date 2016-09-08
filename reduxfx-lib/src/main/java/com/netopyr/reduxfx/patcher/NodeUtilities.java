package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javaslang.Tuple2;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;

class NodeUtilities {

    private static final Logger LOG = LoggerFactory.getLogger(NodeUtilities.class);


    public static void setAllAttributes(Node node, VNode vNode) {
        setProperties(node, vNode.getProperties());
        setEventHandlers(node, vNode.getEventHandlers());
        setChangeListeners(node, vNode.getChangeListeners());
        setInvalidationListeners(node, vNode.getInvalidationListeners());
    }

    @SuppressWarnings("unchecked")
    static void setProperties(Node node, Map<String, Object> properties) {
        for (final Tuple2<String, Object> property : properties) {

            if (Seq.class.isAssignableFrom(property._2.getClass())) {
                final Option<MethodHandle> getter = getGetter(node.getClass(), property._1);
                if (getter.isDefined()) {
                    try {
                        ((ObservableList) getter.get().invoke(node)).setAll(((Seq)property._2).toJavaList());
                    } catch (Throwable throwable) {
                        LOG.error("Unable to set JavaFX property " + property, throwable);
                    }
                }

            } else {
                final Option<MethodHandle> setter = getSetter(node.getClass(), property._1, property._2.getClass());
                if (setter.isDefined()) {
                    try {
                        setter.get().invoke(node, property._2);
                    } catch (Throwable throwable) {
                        LOG.error("Unable to set JavaFX property " + property, throwable);
                    }
                }
            }
        }
    }

    static void setEventHandlers(Node node, Map<String, EventHandler<?>> eventHandlers) {
        for (final Tuple2<String, EventHandler<?>> eventHandler : eventHandlers) {
            final Option<MethodHandle> setter = getEventSetter(node.getClass(), eventHandler._1);
            if (setter.isDefined()) {
                try {
                    setter.get().invoke(node, eventHandler._2);
                } catch (Throwable throwable) {
                    LOG.error("Unable to set JavaFX EventHandler " + eventHandler._1, throwable);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void setChangeListeners(Node node, Map<String, ChangeListener<?>> changeListeners) {
        for (final Tuple2<String, ChangeListener<?>> changeListener : changeListeners) {
            final Option<MethodHandle> getter = getPropertyGetter(node.getClass(), changeListener._1);
            if (getter.isDefined()) {
                try {
                    final Property property = (Property<?>) getter.get().invoke(node);
                    final ChangeListener oldListener = (ChangeListener) node.getProperties().get(property.getName() + ".change");
                    if (Objects.equals(oldListener, changeListener._2)) {
                        continue;
                    }
                    if (oldListener != null) {
                        property.removeListener(oldListener);
                    }
                    property.addListener(changeListener._2);
                    node.getProperties().put(property.getName() + ".change", changeListener._2);
                } catch (Throwable throwable) {
                    LOG.error("Unable to set JavaFX ChangeListener " + changeListener._1, throwable);
                }
            }
        }
    }

    public static void setInvalidationListeners(Node node, Map<String, InvalidationListener> invalidationListeners) {
        for (final Tuple2<String, InvalidationListener> invalidationListener : invalidationListeners) {
            final Option<MethodHandle> getter = getPropertyGetter(node.getClass(), invalidationListener._1);
            if (getter.isDefined()) {
                try {
                    final Property property = (Property<?>) getter.get().invoke(node);
                    final InvalidationListener oldListener = (InvalidationListener) node.getProperties().get(property.getName() + ".invalidation");
                    if (Objects.equals(oldListener, invalidationListener._2)) {
                        continue;
                    }
                    if (oldListener != null) {
                        property.removeListener(oldListener);
                    }
                    property.addListener(invalidationListener._2);
                    node.getProperties().put(property.getName() + ".invalidation", invalidationListener._2);
                } catch (Throwable throwable) {
                    LOG.error("Unable to set JavaFX ChangeListener " + invalidationListener._1, throwable);
                }
            }
        }
    }

    private static Option<MethodHandle> getSetter(Class<? extends Node> clazz, String propertyName, Class<?> paramType) {
        final String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        Method method = null;
        try {
            method = clazz.getMethod(setterName, paramType);
        } catch (NoSuchMethodException e) {
            // ignore
        }

        if (method == null && Boolean.class.equals(paramType)) {
            try {
                method = clazz.getMethod(setterName, boolean.class);
            } catch (NoSuchMethodException e) {
                // ignore
            }
        }

        if (method == null) {
            LOG.error("Unable to find setter for property {} in class {}", propertyName, clazz);
            return Option.none();
        }

        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(method);
            return Option.of(methodHandle);
        } catch (IllegalAccessException e) {
            LOG.error("Setter for property {} in class {} is not accessible", propertyName, clazz);
            return Option.none();
        }
    }

    private static Option<MethodHandle> getGetter(Class<? extends Node> clazz, String propertyName) {
        final String getterName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        Method method = null;
        try {
            method = clazz.getMethod("is" + getterName);
        } catch (NoSuchMethodException e) {
            // ignore
        }

        if (method == null) {
            try {
                method = clazz.getMethod("get" + getterName);
            } catch (NoSuchMethodException e) {
                // ignore
            }
        }

        if (method == null) {
            LOG.error("Unable to find getter for property {} in class {}", propertyName, clazz);
            return Option.none();
        }

        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(method);
            return Option.of(methodHandle);
        } catch (IllegalAccessException e) {
            LOG.error("Getter for property {} in class {} is not accessible", propertyName, clazz);
            return Option.none();
        }
    }

    private static Option<MethodHandle> getEventSetter(Class<? extends Node> clazz, String propertyName) {
        final String setterName = "setOn" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        Method method = null;
        try {
            method = clazz.getMethod(setterName, EventHandler.class);
        } catch (NoSuchMethodException e) {
            // ignore
        }

        if (method == null) {
            LOG.error("Unable to find setter for EventHandler {} in class {}", propertyName, clazz);
            return Option.none();
        }

        try {
            final MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(method);
            return Option.of(methodHandle);
        } catch (IllegalAccessException e) {
            LOG.error("Setter for EventHandler {} in class {} is not accessible", propertyName, clazz);
            return Option.none();
        }
    }

    private static Option<MethodHandle> getPropertyGetter(Class<? extends Node> clazz, String propertyName) {
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
}
