package com.netopyr.reduxfx.impl.component.property;

import com.netopyr.reduxfx.component.command.ObjectChangedCommand;
import io.reactivex.Observable;
import javafx.application.Platform;
import javafx.beans.property.ObjectPropertyBase;

import java.util.function.BiConsumer;

public final class ReduxFXObjectProperty<T> extends ObjectPropertyBase<T> {

    private final Object bean;
    private final String name;

    private boolean updating;

    public ReduxFXObjectProperty(Object bean, String name, Observable<ObjectChangedCommand<?>> commandReceiver, BiConsumer<T, T> dispatcher) {
        this.bean = bean;
        this.name = name;
        commandReceiver.forEach(
                command -> Platform.runLater(
                        () -> {
                            try {
                                updating = true;
                                set((T) command.getNewValue());
                            } finally {
                                updating = false;
                            }
                        }));
        this.addListener((source, oldValue, newValue) -> {
            if (! updating) {
                dispatcher.accept(oldValue, newValue);
            }
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

}
