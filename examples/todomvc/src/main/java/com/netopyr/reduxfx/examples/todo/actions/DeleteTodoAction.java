package com.netopyr.reduxfx.examples.todo.actions;

import com.netopyr.reduxfx.examples.todo.updater.Updater;
import com.netopyr.reduxfx.examples.todo.state.TodoEntry;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A {@code DeleteTodoAction} is passed to the {@link Updater} when a {@link TodoEntry} should be deleted.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Updater}, which performs the actual change.
 */
public final class DeleteTodoAction {

    private final int id;

    DeleteTodoAction(int id) {
        this.id = id;
    }

    /**
     * This is the getter for the {@code id} of the {@link TodoEntry} that needs to be deleted
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
