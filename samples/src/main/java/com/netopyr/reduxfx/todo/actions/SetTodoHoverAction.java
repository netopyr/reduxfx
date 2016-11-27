package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@code SetTodoHoverAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the
 * {@code hover}-flag of a {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be changed. The
 * {@code hover}-flag signals, if the mouse hovers over the item.
 */
public final class SetTodoHoverAction implements Action {

    private final int id;
    private final boolean value;

    SetTodoHoverAction(int id, boolean value) {
        this.id = id;
        this.value = value;
    }

    /**
     * This is the getter for the {@code id} of the {@link com.netopyr.reduxfx.todo.state.TodoEntry} which
     * {@code hover}-flag needs to be changed.
     *
     * @return the {@code id}
     */
    public int getId() {
        return id;
    }

    /**
     * This is the getter of the new value for the {@code hover}-flag.
     *
     * @return the new value
     */
    public boolean isValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("value", value)
                .toString();
    }
}
