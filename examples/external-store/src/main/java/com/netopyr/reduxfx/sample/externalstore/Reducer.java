package com.netopyr.reduxfx.sample.externalstore;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class Reducer {

    private Reducer() {
    }

    public static Integer reduce(Integer state, Object action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        return action instanceof Action ? reduce(state, (Action) action) : state;
    }

    private static Integer reduce(Integer state, Action action) {
        switch (action) {
            case INC_COUNTER:
                return state + 1;
            default:
                return state;
        }
    }
}
