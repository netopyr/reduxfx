package com.netopyr.reduxfx.examples.helloworld.state;

import com.netopyr.reduxfx.examples.helloworld.updater.Updater;
import com.netopyr.reduxfx.examples.helloworld.view.MainView;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An instance of the class {@code AppState} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Updater}. The {@code Updater} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link MainView}-function, which calculates the new virtual Scenegraph.
 */
public final class AppState {

    private final int counter;


    private AppState(int counter) {
        this.counter = counter;
    }

    /**
     * The method {@code create} returns a new instance of {@code AppState} with the counter set to its default value {@code 0}.
     *
     * @return the new {@code AppState}
     */
    public static AppState create() {
        return new AppState(0);
    }


    /**
     * This is the getter of the {@code counter}.
     *
     * @return the {@code counter}
     */
    public int getCounter() {
        return counter;
    }

    /**
     * The method {@code withCounter} creates a copy of this {@code AppState} with the {@code counter} set to
     * the given value.
     *
     * @param counter the new {@code counter}
     * @return the created {@code AppState}
     */
    public AppState withCounter(int counter) {
        return new AppState(counter);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("counter", counter)
                .toString();
    }
}
