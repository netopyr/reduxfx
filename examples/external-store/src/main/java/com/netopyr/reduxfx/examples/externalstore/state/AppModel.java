package com.netopyr.reduxfx.examples.externalstore.state;

import com.netopyr.reduxfx.examples.externalstore.reducer.Reducer;
import com.netopyr.reduxfx.examples.externalstore.view.MainView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Reducer}. The {@code Reducer} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link MainView}-function, which calculates the new virtual Scenegraph.
 */
public final class AppModel {

    private final int counter;


    private AppModel(int counter) {
        this.counter = counter;
    }

    /**
     * The method {@code create} returns a new instance of {@code AppModel} with the counter set to its default value {@code 0}.
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
     * @param counter the new {@code counter}
     * @return the created {@code AppModel}
     */
    public AppModel withCounter(int counter) {
        return new AppModel(counter);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("counter", counter)
                .toString();
    }
}
