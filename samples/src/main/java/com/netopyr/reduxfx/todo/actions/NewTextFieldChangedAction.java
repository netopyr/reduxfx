package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@code NewTextFieldChangedAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the value
 * of the {@link javafx.scene.control.TextField} for new {@link com.netopyr.reduxfx.todo.state.TodoEntry}s has changed.
 */
public final class NewTextFieldChangedAction implements Action {

    private final String text;

    NewTextFieldChangedAction(String text) {
        this.text = text;
    }

    /**
     * This is the getter of the new text
     *
     * @return the new text
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .toString();
    }
}
