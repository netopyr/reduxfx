package com.netopyr.reduxfx.component.property;

import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import rx.Observable;

public final class ReduxFXReadOnlyObjectProperty<T> extends ReadOnlyObjectPropertyBase<T> {

    private final Object bean;
    private final String name;

    private T value;

    public ReduxFXReadOnlyObjectProperty(Object bean, String name, Observable<ObjectChangedCommand<?>> receiver) {
        this.bean = bean;
        this.name = name;
        receiver.forEach(command -> {
            this.value = (T) command.getNewValue();
            this.fireValueChangedEvent();
        });
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T get() {
        return value;
    }




}
