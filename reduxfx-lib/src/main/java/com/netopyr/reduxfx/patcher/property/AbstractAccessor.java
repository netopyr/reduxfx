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

abstract class AbstractAccessor<V_TYPE, ACTION, FX_TYPE> implements Accessor<V_TYPE, ACTION> {

    private final MethodHandle propertyGetter;
    private final Consumer<ACTION> dispatcher;

    AbstractAccessor(MethodHandle propertyGetter, Consumer<ACTION> dispatcher) {
        this.propertyGetter = propertyGetter;
        this.dispatcher = dispatcher;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Node node, VProperty<V_TYPE, ACTION> vProperty) {

        final ReadOnlyProperty<FX_TYPE> property;
        try {
            property = (ReadOnlyProperty) propertyGetter.invoke(node);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read property " + vProperty.getName() + " from Node-class " + node.getClass(), throwable);
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

    protected abstract V_TYPE fxToV(FX_TYPE value);

    protected abstract FX_TYPE vToFX(V_TYPE value);

    protected abstract void setValue(ReadOnlyProperty<FX_TYPE> property, FX_TYPE value);

    @SuppressWarnings("unchecked")
    private void clearListeners(Node node, ReadOnlyProperty<FX_TYPE> property) {
        final ChangeListener<FX_TYPE> changeListener = (ChangeListener) node.getProperties().get(property.getName() + ".change");
        if (changeListener != null) {
            property.removeListener(changeListener);
        }

        final InvalidationListener invalidationListener = (InvalidationListener) node.getProperties().get(property.getName() + ".invalidation");
        if (invalidationListener != null) {
            property.removeListener(invalidationListener);
        }
    }

    private void setChangeListener(Node node, ReadOnlyProperty<FX_TYPE> property, VChangeListener<? super V_TYPE, ACTION> listener, Consumer<ACTION> dispatcher) {
        final ChangeListener<FX_TYPE> newListener = (source, oldValue, newValue) -> {
            final ACTION action = listener.onChange(fxToV(oldValue), fxToV(newValue));
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".change", newListener);
    }

    private void setInvalidationListener(Node node, ReadOnlyProperty<FX_TYPE> property, VInvalidationListener<ACTION> listener, Consumer<ACTION> dispatcher) {
        final InvalidationListener newListener = (source) -> {
            final ACTION action = listener.onInvalidation();
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        node.getProperties().put(property.getName() + ".invalidation", newListener);
    }
}
