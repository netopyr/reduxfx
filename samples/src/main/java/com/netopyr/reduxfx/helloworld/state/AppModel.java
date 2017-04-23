package com.netopyr.reduxfx.helloworld.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 *
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link com.netopyr.reduxfx.helloworld.updater.Updater}. The {@code Updater} gets the current state together with the
 * Action that should be performed and calculates the new state from that.
 *
 * The new state is passed to the {@link com.netopyr.reduxfx.helloworld.view.MainView}-function, which calculates the new
 * virtual Scenegraph.
 */
public final class AppModel {

    private final int counter;


    private AppModel(int counter) {
        this.counter = counter;
    }

    /**
     * The method {@code create} returns a new instance of {@code AppModel} with the counter set to its default values {@code 0}.
     *
     * @return the new {@code AppModel}
     */
    public static AppModel create() {
        return new AppModel(0);
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
     * The method {@code withCounter} creates a copy of this {@code AppModel} with the {@code counter} set to
     * the given value.
     *
     * @param newCounter the new {@code counter}
     * @return the created {@code AppModel}
     */
    public AppModel withCounter(int newCounter) {
        return new AppModel(newCounter);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("counter", counter)
                .toString();
    }
}
