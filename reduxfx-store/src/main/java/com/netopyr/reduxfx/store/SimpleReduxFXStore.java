package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.updater.Update;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.BiFunction;

public class SimpleReduxFXStore<STATE> {

    private final ReduxFXStore<STATE> store;

    public SimpleReduxFXStore(STATE initialState, BiFunction<STATE, Object, STATE> updater) {
        this.store = new ReduxFXStore<>(initialState, (STATE state, Object action) -> Update.of(updater.apply(state, action)));
    }

    public Subscriber<Object> createActionSubscriber() {
        return store.createActionSubscriber();
    }

    public Publisher<STATE> getStatePublisher() {
        return store.getStatePublisher();
    }

}
