package com.netopyr.reduxfx.component;

import com.netopyr.reduxfx.ReduxFX;
import com.netopyr.reduxfx.component.driver.ComponentDriver;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ComponentBase<STATE, ACTION> extends ReduxFX<STATE, ACTION> {

    private final ComponentDriver<ACTION> componentDriver = new ComponentDriver<>();

    public ComponentBase(
            Parent component,
            STATE initialState,
            BiFunction<STATE, ACTION, Update<STATE>> updater,
            Function<STATE, VNode<ACTION>> view
    ) {
        super(initialState, updater, view, component);
        component.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                start();
            } else {
                stop();
            }
        });

        registerDriver(componentDriver);
    }

    public ReadOnlyIntegerProperty createReadOnlyIntegerProperty(Object bean, String name) {
        return componentDriver.createReadOnlyIntegerProperty(bean, name);
    }

    public <T> ReadOnlyObjectProperty<T> createReadOnlyObjectProperty(Object bean, String name) {
        return componentDriver.createReadOnlyObjectProperty(bean, name);
    }


    public <T> ObjectProperty<T> createObjectProperty(Object bean, String name, BiFunction<T, T, ACTION> mapper) {
        return componentDriver.createObjectProperty(bean, name, mapper);
    }


    public <EVENT extends Event> ObjectProperty<EventHandler<EVENT>> createEventHandlerProperty(Object bean, String name) {
        return componentDriver.createEventHandlerProperty(bean, name);
    }
}
