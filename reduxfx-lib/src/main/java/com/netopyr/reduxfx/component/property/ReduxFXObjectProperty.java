package com.netopyr.reduxfx.component.property;

import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import javafx.beans.property.ObjectPropertyBase;
import rx.Observable;
import rx.Observer;

import java.util.function.BiFunction;

public final class ReduxFXObjectProperty<T, ACTION> extends ObjectPropertyBase<T> {

    private final Object bean;
    private final String name;

    public ReduxFXObjectProperty(Object bean, String name, Observable<ObjectChangedCommand<?>> commandReceiver, BiFunction<T, T, ACTION> mapper, Observer<ACTION> actionSender) {
        this.bean = bean;
        this.name = name;
        commandReceiver.forEach(command -> set((T) command.getNewValue()));
        this.addListener((source, oldValue, newValue) -> actionSender.onNext(mapper.apply(oldValue, newValue)));
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
