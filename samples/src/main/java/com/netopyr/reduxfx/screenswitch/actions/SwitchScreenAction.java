package com.netopyr.reduxfx.screenswitch.actions;

import com.netopyr.reduxfx.screenswitch.state.Screen;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@©ode DeleteTodoAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when a
 * {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be deleted.
 */
public final class SwitchScreenAction implements Action {

    private final Screen screen;

    SwitchScreenAction(Screen screen) {
        this.screen = screen;
    }

    /**
     * This is the getter for the {@code id} of the {@link com.netopyr.reduxfx.todo.state.TodoEntry} that needs to be
     * deleted
     *
     * @return the {@code Screen}
     */
    public Screen getScreen() {
        return screen;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("screen", screen)
                .toString();
    }
}
