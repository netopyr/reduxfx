package com.netopyr.reduxfx.examples.fxml.updater;

import com.netopyr.reduxfx.examples.fxml.actions.IncCounterAction;
import com.netopyr.reduxfx.examples.fxml.state.AppState;

import java.util.Objects;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

@SuppressWarnings("WeakerAccess")
public class Updater {

    private Updater() {
    }

    public static AppState update(AppState state, Object action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        // This is part of Javaslang's pattern-matching API. It works similar to the regular switch-case
        // in Java, except that it is much more flexible and that it can be used as an expression.
        // We check which kind of action was received and in that case-branch we specify the value that
        // will be assigned to newState.
        return Match(action).of(

                Case(instanceOf(IncCounterAction.class),
                        incCounterAction -> state.withCounter(state.getCounter() + 1)
                ),

                // This is the default branch of this switch-case. If an unknown Action was passed to the
                // updater, we simple return the old state. This is a convention, that is not needed right
                // now, but will help once you start to decompose your updater.
                Case($(), state)
        );
    }
}
