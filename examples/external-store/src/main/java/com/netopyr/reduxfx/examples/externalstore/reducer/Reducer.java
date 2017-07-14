package com.netopyr.reduxfx.examples.externalstore.reducer;

import com.netopyr.reduxfx.examples.externalstore.actions.IncCounterAction;
import com.netopyr.reduxfx.examples.externalstore.state.AppState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

/**
 * This class implements a reducer as defined by the Redux-architecture. It consists of a single method that
 * takes the current {@link AppState} and an action and returns the new {@code AppState}.
 */
public class Reducer {

    private static final Logger LOG = LoggerFactory.getLogger(Reducer.class);

    private Reducer() {
    }

    /**
     * This method implements the reduce-functionality required for a Redux-store. It takes the current
     * {@link AppState} and an action and returns the new {@code AppState}
     *
     * @param state the current {@code AppState}
     * @param action the action
     * @return the new {@code AppState}
     */
    public static AppState reduce(AppState state, Object action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        // Here we assign the new state
        final AppState newState =

                // This is part of Vavr's pattern-matching API. It works similar to the regular switch-case
                // in Java, except that it is much more flexible and returns a value.
                // We check which kind of action was received and in that case-branch we specify the value that
                // will be assigned to newState.
                Match(action).of(

                        // If the action is a IncCounterAction, we return a new AppState with the
                        // counter increased by one.
                        Case($(instanceOf(IncCounterAction.class)),
                                incCounterAction -> state.withCounter(state.getCounter() + 1)
                        ),

                        // This is the default branch of this switch-case. If an unknown action was passed to the
                        // updater, we simply return the old state. This is a convention, that is not needed right
                        // now, but will help once you start to decompose your reducer.
                        Case($(), state)
                );

        LOG.trace("\nUpdater Old State:\n{}\nUpdater Action:\n{}\nUpdater New State:\n{}\n\n",
                state, action, newState);
        return newState;
    }
}
