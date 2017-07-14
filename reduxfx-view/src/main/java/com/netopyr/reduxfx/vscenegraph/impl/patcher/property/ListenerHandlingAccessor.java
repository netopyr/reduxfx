package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;

import java.util.function.Consumer;

import static com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities.getProperties;

@SuppressWarnings("WeakerAccess")
abstract class ListenerHandlingAccessor implements Accessor {

    protected abstract Object fxToV(Object value);

    protected abstract Object vToFX(Object value);

    @SuppressWarnings("unchecked")
    protected void clearListeners(Object node, ReadOnlyProperty property) {
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
    protected void setChangeListener(Consumer<Object> dispatcher, Object node, ReadOnlyProperty property, VChangeListener listener) {
        final ChangeListener newListener = (source, oldValue, newValue) -> {
            final Object action = listener.onChange(fxToV(oldValue), fxToV(newValue));
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        getProperties(node).put(property.getName() + ".change", newListener);
    }

    protected void setInvalidationListener(Consumer<Object> dispatcher, Object node, ReadOnlyProperty property, VInvalidationListener listener) {
        final InvalidationListener newListener = source -> {
            final Object action = listener.onInvalidation();
            if (action != null) {
                dispatcher.accept(action);
            }
        };
        property.addListener(newListener);
        getProperties(node).put(property.getName() + ".invalidation", newListener);
    }
}
