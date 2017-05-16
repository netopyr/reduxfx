package com.netopyr.reduxfx.examples.screenswitch.state;

import com.netopyr.reduxfx.examples.screenswitch.updater.Updater;
import com.netopyr.reduxfx.examples.screenswitch.view.ViewManager;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An instance of the class {@code AppState} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Updater}. The {@code Updater} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link ViewManager}-function, which calculates the new virtual Scenegraph.
 */
public final class AppState {

    private final Screen screen;

    private AppState(Screen screen) {
        this.screen = Objects.requireNonNull(screen, "The parameter 'screen' must not be null");;
    }


    /**
     * The method {@code create} returns a new instance of {@code AppState} with the {@code screen}-property set
     * to its default value {@code Screen.SCREEN_1}.
     *
     * @return the new {@code AppState}
     */
    public static AppState create() {
        return new AppState(Screen.SCREEN_1);
    }


    /**
     * This is the getter of the {@code screen}.
     *
     * @return the {@code newTodoText}
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * The method {@code withScreen} creates a copy of this {@code AppState} with the {@code screen} set to
     * the given value.
     *
     * @param screen the new {@code Screen}
     * @return the created {@code AppState}
     * @throws NullPointerException if {@code screen} is {@code null}
     */
    public AppState withScreen(Screen screen) {
        return new AppState(screen);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("screen", screen)
                .toString();
    }
}
