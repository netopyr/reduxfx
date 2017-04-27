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
public class ComponentBase<STATE> extends ReduxFXStore<STATE> {

    private final ComponentDriver componentDriver = new ComponentDriver();

    @SafeVarargs
    public ComponentBase(
            Group component,
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Middleware<STATE>... middlewares
    ) {
        super(initialState, updater, middlewares);
        register(componentDriver);
    }

    @SafeVarargs
    public ComponentBase(
            Pane component,
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Middleware<STATE>... middlewares
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


    public <EVENT extends Event> ObjectProperty<EventHandler<EVENT>> createEventHandlerProperty(Object bean, String name) {
        return componentDriver.createEventHandlerProperty(bean, name);
    }

    @FunctionalInterface
    public interface ChangeListener<TYPE> {

        Object onChange(TYPE oldValue, TYPE newValue);

    }
}
