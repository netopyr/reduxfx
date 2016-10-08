package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.function.Consumer;

abstract class AbstractAccessor<TYPE, ACTION> implements PropertyAccessor<TYPE, ACTION> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractAccessor.class);

    private final MethodHandle propertyGetter;
    private final Consumer<ACTION> dispatcher;

    AbstractAccessor(Class<? extends Node> clazz, VPropertyType propertyType, Consumer<ACTION> dispatcher) {
        this.propertyGetter = getPropertyGetter(clazz, propertyType);
        this.dispatcher = dispatcher;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Node node, VProperty<TYPE, ACTION> vProperty) {

        final Property<TYPE> property;
        try {
            property = (Property) propertyGetter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read property " + vProperty.getType() + " from Node-class " + node.getClass(), throwable);
        }

        setValue(property, vProperty.getValue());

        if (vProperty.getChangeListener().isDefined()) {
            setChangeListener(node, property, vProperty.getChangeListener().get());
        }

        if (vProperty.getInvalidationListener().isDefined()) {
            setInvalidationListener(node, property, vProperty.getInvalidationListener().get());
        }
    }

    protected abstract void setValue(Property<TYPE> property, TYPE value);

    @SuppressWarnings("unchecked")
    private void setChangeListener(Node node, Property<TYPE> property, VChangeListener<? super TYPE, ACTION> listener) {
        final ChangeListener<TYPE> oldListener = (ChangeListener) node.getProperties().get(property.getName() + ".change");
        if (oldListener != null) {
            property.removeListener(oldListener);
        }

        final ChangeListener<TYPE> newListener = (source, oldValue, newValue) -> {
            final ACTION action = listener.onChange(oldValue, newValue);
            dispatcher.accept(action);
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".change", newListener);
    }

    @SuppressWarnings("unchecked")
    private void setInvalidationListener(Node node, Property<TYPE> property, VInvalidationListener<ACTION> listener) {
        final InvalidationListener oldListener = (InvalidationListener) node.getProperties().get(property.getName() + ".invalidation");
        if (oldListener != null) {
            property.removeListener(oldListener);
        }

        final InvalidationListener newListener = (source) -> {
            final ACTION action = listener.onInvalidation();
            dispatcher.accept(action);
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".invalidation", newListener);
    }

    private MethodHandle getPropertyGetter(Class<? extends Node> clazz, VPropertyType propertyType) {
        final String propertyName = propertyType.getName();
        final String getterName = propertyName + "Property";

        final Method method;
        try {
            method = clazz.getMethod(getterName);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to find property-getter of property " + propertyName + " in class " + clazz, e);
        }

        try {
            return MethodHandles.publicLookup().unreflect(method);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Property-getter of property " + propertyName + " in class " + clazz + " is not accessible", e);
        }
    }
}
