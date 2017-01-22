package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

abstract class AbstractAccessor implements Accessor {

    private final MethodHandle propertyGetter;
    private final Consumer<Object> dispatcher;

    AbstractAccessor(MethodHandle propertyGetter, Consumer<Object> dispatcher) {
        this.propertyGetter = propertyGetter;
        this.dispatcher = dispatcher;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Node node, String name, VProperty vProperty) {

        final ReadOnlyProperty property;
        try {
            property = (ReadOnlyProperty) propertyGetter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read property " + name + " from Node-class " + node.getClass(), throwable);
        }

        clearListeners(node, property);

        if (vProperty.isValueDefined()) {
            setValue(property, vToFX(vProperty.getValue()));
        }

        if (vProperty.getChangeListener().isDefined()) {
            setChangeListener(node, property, vProperty.getChangeListener().get(), dispatcher);
        }

        if (vProperty.getInvalidationListener().isDefined()) {
            setInvalidationListener(node, property, vProperty.getInvalidationListener().get(), dispatcher);
        }
    }

    protected abstract Object fxToV(Object value);

    protected abstract Object vToFX(Object value);

    protected abstract void setValue(ReadOnlyProperty property, Object value);

    @SuppressWarnings("unchecked")
    private void clearListeners(Node node, ReadOnlyProperty property) {
        final ChangeListener changeListener = (ChangeListener) node.getProperties().get(property.getName() + ".change");
        if (changeListener != null) {
            property.removeListener(changeListener);
        }

        final InvalidationListener invalidationListener = (InvalidationListener) node.getProperties().get(property.getName() + ".invalidation");
        if (invalidationListener != null) {
            property.removeListener(invalidationListener);
        }
    }

    private void setChangeListener(Node node, ReadOnlyProperty property, VChangeListener listener, Consumer<Object> dispatcher) {
        final ChangeListener newListener = (source, oldValue, newValue) -> {
            final Object action = listener.onChange(fxToV(oldValue), fxToV(newValue));
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".change", newListener);
    }

    private void setInvalidationListener(Node node, ReadOnlyProperty property, VInvalidationListener listener, Consumer<Object> dispatcher) {
        final InvalidationListener newListener = (source) -> {
            final Object action = listener.onInvalidation();
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".invalidation", newListener);
    }
}
