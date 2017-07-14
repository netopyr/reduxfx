package com.netopyr.reduxfx.component;

import com.netopyr.reduxfx.component.impl.ComponentDriver;
import com.netopyr.reduxfx.middleware.Middleware;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class ComponentBase<S> extends ReduxFXStore<S> {

    private final ComponentDriver componentDriver = new ComponentDriver();

    @SafeVarargs
    public ComponentBase(
            Group component,
            S initialState,
            BiFunction<S, Object, Update<S>> updater,
            Middleware<S>... middlewares
    ) {
        super(initialState, updater, middlewares);
        register(componentDriver);
    }

    @SafeVarargs
    public ComponentBase(
            Pane component,
            S initialState,
            BiFunction<S, Object, Update<S>> updater,
            Middleware<S>... middlewares
    ) {
        super(initialState, updater);
        register(componentDriver);
    }

    public ReadOnlyIntegerProperty createReadOnlyIntegerProperty(Object bean, String name) {
        return componentDriver.createReadOnlyIntegerProperty(bean, name);
    }

    public <T> ReadOnlyObjectProperty<T> createReadOnlyObjectProperty(Object bean, String name) {
        return componentDriver.createReadOnlyObjectProperty(bean, name);
    }


    public <T> ObjectProperty<T> createObjectProperty(Object bean, String name, ChangeListener<T> listener) {
        return componentDriver.createObjectProperty(bean, name, listener);
    }


    public <E extends Event> ObjectProperty<EventHandler<E>> createEventHandlerProperty(Object bean, String name) {
        return componentDriver.createEventHandlerProperty(bean, name);
    }

    @FunctionalInterface
    public interface ChangeListener<T> {

        Object onChange(T oldValue, T newValue);

    }
}
