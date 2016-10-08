package com.netopyr.reduxfx.updater;

import com.netopyr.reduxfx.Reducer;
import javaslang.collection.Array;

public class Updater<STATE, ACTION> {

    private final Reducer<STATE, ACTION> reducer;
    private Array<Runnable> listeners = Array.empty();

    private STATE currentState;
    private boolean dispatching;

    public Updater(Reducer<STATE, ACTION> reducer) {
        this.reducer = reducer;
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
            currentState = reducer.reduce(currentState, action);
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
