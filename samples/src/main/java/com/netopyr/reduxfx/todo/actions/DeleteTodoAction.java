package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@©ode DeleteTodoAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when a
 * {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be deleted.
 */
public final class DeleteTodoAction implements Action {

    private final int id;

    DeleteTodoAction(int id) {
        this.id = id;
    }

    /**
     * This is the getter for the {@code id} of the {@link com.netopyr.reduxfx.todo.state.TodoEntry} that needs to be
     * deleted
     *
     * @return the {@code id}
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
