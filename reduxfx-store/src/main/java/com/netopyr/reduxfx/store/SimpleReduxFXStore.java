package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.middleware.Middleware;
import com.netopyr.reduxfx.updater.Update;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.BiFunction;

public class SimpleReduxFXStore<S> {

    private final ReduxFXStore<S> store;

    @SafeVarargs
    public SimpleReduxFXStore(S initialState, BiFunction<S, Object, S> updater, Middleware<S>... middlewares) {
        this.store = new ReduxFXStore<>(initialState, (S state, Object action) -> Update.of(updater.apply(state, action)), middlewares);
    }

    public Subscriber<Object> createActionSubscriber() {
        return store.createActionSubscriber();
    }

    public Publisher<S> getStatePublisher() {
        return store.getStatePublisher();
    }

}
