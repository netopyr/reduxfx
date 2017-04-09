package com.netopyr.reduxfx.screenswitch.state;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 *
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link com.netopyr.reduxfx.todo.updater.Updater}. The {@code Updated} gets the current state together with the
 * {@link com.netopyr.reduxfx.todo.actions.Action} that should be performed and calculates the new state from that.
 *
 * The new state is passed to the {@link com.netopyr.reduxfx.todo.view.MainView}-function, which calculates the new
 * virtual Scenegraph.
 */
public final class AppModel {

    private final Screen screen;

    private AppModel(Screen screen) {
        this.screen = screen;
    }


    /**
     * The method {@code create} returns a new instance of {@code AppModel} with all properties set to their default values.
     *
     * Default values are: {newTodoText: "", todos: Array.empty(), filter: Filter.ALL}
     */
    public static AppModel create() {
        return new AppModel(Screen.SCREEN_1);
    }


    /**
     * This is the getter of the {@code newTodoText}.
     *
     * If a new todo-entry should be created, this property contains the text that the new todo-entry should contain.
     *
     * @return the {@code newTodoText}
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * The method {@code withNewTodoText} creates a copy of this {@code AppModel} with the {@code newTodoText} set to
     * the given value.
     *
     * @param newScreen the new {@code Screen}
     * @return the created {@code AppModel}
     * @throws NullPointerException if {@code newNewTodoText} is {@code null}
     */
    public AppModel withScreen(Screen newScreen) {
        Objects.requireNonNull(newScreen, "The parameter 'newNewTodoText' must not be null");
        return new AppModel(newScreen);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("screen", screen)
                .toString();
    }
}
