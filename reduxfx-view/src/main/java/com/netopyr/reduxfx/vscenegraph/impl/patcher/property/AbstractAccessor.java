package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.property.ReadOnlyProperty;

import java.lang.invoke.MethodHandle;
import java.util.Objects;
import java.util.function.Consumer;

abstract class AbstractAccessor extends ListenerHandlingAccessor {

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

    protected abstract void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value);

}
