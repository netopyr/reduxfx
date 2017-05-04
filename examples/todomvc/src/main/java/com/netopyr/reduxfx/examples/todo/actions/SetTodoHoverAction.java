package com.netopyr.reduxfx.examples.todo.actions;

import com.netopyr.reduxfx.examples.todo.state.TodoEntry;
import com.netopyr.reduxfx.examples.todo.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A {@code SetTodoHoverAction} is passed to the {@link Updater} when the {@code hover}-flag of a {@link TodoEntry}
 * should be changed. The {@code hover}-flag signals, if the mouse hovers over the item.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Updater}, which performs the actual change.
 */
public final class SetTodoHoverAction {

    private final int id;
    private final boolean value;

    SetTodoHoverAction(int id, boolean value) {
        this.id = id;
        this.value = value;
    }

    /**
     * This is the getter for the {@code id} of the {@link TodoEntry} which {@code hover}-flag needs to be changed.
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("value", value)
                .toString();
    }
}
