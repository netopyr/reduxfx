package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.todo.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An {@code AddTodoAction} is passed to the {@link Updater} when a new
 * {@link TodoEntry} should be created and added to the list.
 */
public final class AddTodoAction implements Action {

    AddTodoAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
