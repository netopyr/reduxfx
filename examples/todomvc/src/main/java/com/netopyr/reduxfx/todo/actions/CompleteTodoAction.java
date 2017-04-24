package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.todo.updater.Updater;

/**
 * A {@code CompleteTodoAction} is passed to the {@link Updater} when the
 * {@code completed}-flag of a {@link TodoEntry} needs to be toggled.
 */
public final class CompleteTodoAction implements Action {

    private final int id;

    CompleteTodoAction(int id) {
        this.id = id;
    }

    /**
     * This is the getter for the {@code id} of the {@link TodoEntry} which
     * {@code completed}-flag needs to be toggled
     *
     * @return the {@code id}
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .toString();
    }
}
