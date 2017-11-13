package com.netopyr.reduxfx.examples.helloworld.updater;

import com.netopyr.reduxfx.examples.helloworld.actions.IncCounterAction;
import com.netopyr.reduxfx.updater.Update;

import java.util.Objects;

/**
 * The {@code Updater} is the heart of every ReduxFX-application. This is where the main application logic resides.
 * <p>
 * An {@code Updater} consists of a single function ({@link #update(Integer, Object)} in this class), which takes
 * the current state (an {@code int}) and an action and calculates the new state from that.
 * <p>
 * Please note that {@code Updater} has no internal state. Everything that is needed for {@code update} is passed in
 * the parameters. This makes it very easy to understand the code and write tests for it.
 */
public class Updater {

    private Updater() {
    }

    /**
     * This method takes the current state (an {@code int}) and an action and calculates the new state from that.
     * <p>
     * Please note that {@code update} does not require any internal state. Everything that is needed, is passed in the
     * parameters. Also {@code update} has no side effects. It is a pure function. This makes it very easy to understand
     * the code and write tests for it.
     *
     * @param counter  the current value of the counter
     * @param action the action that needs to be performed
     * @return the new {@code AppState}
     * @throws NullPointerException if counter or action are {@code null}
     */
    public static Update<Integer> update(Integer counter, Object action) {
        Objects.requireNonNull(counter, "The parameter 'counter' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        return Update.of(
                action instanceof IncCounterAction? counter + 1 : counter
        );
    }
}
