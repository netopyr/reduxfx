package com.netopyr.reduxfx.component.impl;

import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import io.reactivex.Flowable;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import org.reactivestreams.Publisher;

public final class ReduxFXReadOnlyObjectProperty<T> extends ReadOnlyObjectPropertyBase<T> {

    private final Object bean;
    private final String name;

    private T value;

    @SuppressWarnings("unchecked")
    public ReduxFXReadOnlyObjectProperty(Object bean, String name, Publisher<ObjectChangedCommand<?>> receiver) {
        this.bean = bean;
        this.name = name;
        Flowable.fromPublisher(receiver).forEach(command -> {
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
