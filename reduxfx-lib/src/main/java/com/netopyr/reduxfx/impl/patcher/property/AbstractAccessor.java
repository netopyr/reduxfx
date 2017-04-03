package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;

import java.lang.invoke.MethodHandle;
import java.util.Objects;
import java.util.function.Consumer;

import static com.netopyr.reduxfx.impl.patcher.NodeUtilities.getProperties;

abstract class AbstractAccessor implements Accessor {

    private final MethodHandle propertyGetter;

    AbstractAccessor(MethodHandle propertyGetter) {
        this.propertyGetter = Objects.requireNonNull(propertyGetter, "PropertyGetter must not be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {

        final ReadOnlyProperty property;
        try {
            property = (ReadOnlyProperty) propertyGetter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read property " + name + " from Node-class " + node.getClass(), throwable);
        }

        clearListeners(node, property);

        if (vProperty.isValueDefined()) {
            setValue(dispatcher, property, vToFX(vProperty.getValue()));
        }

        if (vProperty.getChangeListener().isDefined()) {
            setChangeListener(dispatcher, node, property, vProperty.getChangeListener().get());
        }

        if (vProperty.getInvalidationListener().isDefined()) {
            setInvalidationListener(dispatcher, node, property, vProperty.getInvalidationListener().get());
        }
    }

    protected abstract Object fxToV(Object value);

    protected abstract Object vToFX(Object value);

    protected abstract void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value);

    @SuppressWarnings("unchecked")
    private void clearListeners(Object node, ReadOnlyProperty property) {
        final ChangeListener changeListener = (ChangeListener) getProperties(node).get(property.getName() + ".change");
        if (changeListener != null) {
            property.removeListener(changeListener);
        }

        final InvalidationListener invalidationListener = (InvalidationListener) getProperties(node).get(property.getName() + ".invalidation");
        if (invalidationListener != null) {
            property.removeListener(invalidationListener);
        }
    }

    @SuppressWarnings("unchecked")
    private void setChangeListener(Consumer<Object> dispatcher, Object node, ReadOnlyProperty property, VChangeListener listener) {
        final ChangeListener newListener = (source, oldValue, newValue) -> {
            final Object action = listener.onChange(fxToV(oldValue), fxToV(newValue));
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        getProperties(node).put(property.getName() + ".change", newListener);
    }

    private void setInvalidationListener(Consumer<Object> dispatcher, Object node, ReadOnlyProperty property, VInvalidationListener listener) {
        final InvalidationListener newListener = (source) -> {
            final Object action = listener.onInvalidation();
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        getProperties(node).put(property.getName() + ".invalidation", newListener);
    }
}
