package com.netopyr.reduxfx.component.property;

import com.netopyr.reduxfx.component.command.IntegerChangedCommand;
import io.reactivex.Observable;
import javafx.beans.property.ReadOnlyIntegerPropertyBase;

public final class ReduxFXReadOnlyIntegerProperty extends ReadOnlyIntegerPropertyBase {

    private final Object bean;
    private final String name;

    private int value;

    public ReduxFXReadOnlyIntegerProperty(Object bean, String name, Observable<IntegerChangedCommand> receiver) {
        this.bean = bean;
        this.name = name;
        receiver.forEach(command -> {
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
