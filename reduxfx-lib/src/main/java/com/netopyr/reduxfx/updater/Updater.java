package com.netopyr.reduxfx.updater;

import javaslang.collection.Array;

import java.util.function.BiFunction;

public class Updater<STATE, ACTION> {

    private final BiFunction<STATE, ACTION, STATE> update;
    private Array<Runnable> listeners = Array.empty();

    private STATE currentState;
    private boolean dispatching;

    public Updater(BiFunction<STATE, ACTION, STATE> update) {
        this.update = update;
    }

    public STATE getState() {
        return currentState;
    }

    public ACTION dispatch(ACTION action) {
        if (dispatching) {
            throw new IllegalStateException("Reducers may not dispatch actions.");
        }

        try {
            dispatching = true;
            currentState = update.apply(currentState, action);
        } finally {
            dispatching = false;
        }

        for (final Runnable listener : listeners) {
            listener.run();
        }

        return action;
    }

    public Subscription subscribe(Runnable listener) {
        listeners = listeners.append(listener);
        return () -> listeners = listeners.remove(listener);
    }
}
