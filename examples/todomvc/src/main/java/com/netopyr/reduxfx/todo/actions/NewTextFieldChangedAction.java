package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.todo.updater.Updater;

import java.util.Objects;

/**
 * A {@code NewTextFieldChangedAction} is passed to the {@link Updater} when the value
 * of the {@link javafx.scene.control.TextField} for new {@link TodoEntry}s has changed.
 */
public final class NewTextFieldChangedAction implements Action {

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
