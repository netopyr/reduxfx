package com.netopyr.reduxfx.examples.todo.actions;

import com.netopyr.reduxfx.examples.todo.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.netopyr.reduxfx.examples.todo.state.TodoEntry;

import java.util.Objects;

/**
 * A {@code NewTextFieldChangedAction} is passed to the {@link Updater} when the value of the
 * {@code TextField} for the new {@link TodoEntry}s has changed.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Updater}, which performs the actual change.
 */
public final class NewTextFieldChangedAction {

    private final String text;

    NewTextFieldChangedAction(String text) {
        Objects.requireNonNull(text, "The parameter 'text' must not be null");
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("text", text)
                .toString();
    }
}
