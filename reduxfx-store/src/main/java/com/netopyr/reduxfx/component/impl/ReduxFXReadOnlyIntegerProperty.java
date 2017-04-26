package com.netopyr.reduxfx.component.impl;

import com.netopyr.reduxfx.component.command.IntegerChangedCommand;
import io.reactivex.Flowable;
import javafx.beans.property.ReadOnlyIntegerPropertyBase;
import org.reactivestreams.Publisher;

public final class ReduxFXReadOnlyIntegerProperty extends ReadOnlyIntegerPropertyBase {

    private final Object bean;
    private final String name;

    private int value;

    public ReduxFXReadOnlyIntegerProperty(Object bean, String name, Publisher<IntegerChangedCommand> receiver) {
        this.bean = bean;
        this.name = name;
        Flowable.fromPublisher(receiver).forEach(command -> {
            this.value = command.getNewValue();
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
    public int get() {
        return value;
    }




}
