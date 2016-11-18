package com.netopyr.reduxfx.component.property;

import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import io.reactivex.Observable;
import javafx.beans.property.ObjectPropertyBase;

import java.util.function.BiConsumer;

public final class ReduxFXObjectProperty<T> extends ObjectPropertyBase<T> {

    private final Object bean;
    private final String name;

    public ReduxFXObjectProperty(Object bean, String name, Observable<ObjectChangedCommand<?>> commandReceiver, BiConsumer<T, T> dispatcher) {
        this.bean = bean;
        this.name = name;
        commandReceiver.forEach(command -> set((T) command.getNewValue()));
        this.addListener((source, oldValue, newValue) -> dispatcher.accept(oldValue, newValue));
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

}
