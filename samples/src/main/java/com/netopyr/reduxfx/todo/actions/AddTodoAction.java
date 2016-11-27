package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An {@code AddTodoAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when a new
 * {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be created and added to the list.
 */
public final class AddTodoAction implements Action {

    AddTodoAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
