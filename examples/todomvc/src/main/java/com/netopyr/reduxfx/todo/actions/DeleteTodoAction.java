package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.todo.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A {@code DeleteTodoAction} is passed to the {@link Updater} when a
 * {@link TodoEntry} should be deleted.
 */
public final class DeleteTodoAction implements Action {

    private final int id;

    DeleteTodoAction(int id) {
        this.id = id;
    }

    /**
     * This is the getter for the {@code id} of the {@link TodoEntry} that needs to be
     * deleted
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
