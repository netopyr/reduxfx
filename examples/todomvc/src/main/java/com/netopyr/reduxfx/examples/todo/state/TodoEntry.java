package com.netopyr.reduxfx.examples.todo.state;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An instance of the class {@code TodoEntry} contains all state of a single entry in the todo-list.
 */
public final class TodoEntry {

    private final int id;
    private final String text;
    private final boolean completed;
    private final boolean hover;
    private final boolean editMode;

    private TodoEntry(int id, String text, boolean completed, boolean hover, boolean editMode) {
        this.id = id;
        this.text = Objects.requireNonNull(text, "The parameter 'text' must not be null");
        this.completed = completed;
        this.hover = hover;
        this.editMode = editMode;
    }


    /**
     * The method {@code create} returns a new instance of {@code AppState} with all properties set to their default values.
     * <p>
     * Default values are: {id: 0, text: "", completed: false, hover: false, editMode: false}
     *
     * @return the new {@code TodoEntry}
     */
    public static TodoEntry create() {
        return new TodoEntry(0, "", false, false, false);
    }


    /**
     * This is the getter of the entry's id.
     *
     * @return the {@code id}
     */
    public int getId() {
        return id;
    }

    /**
     * The method {@code withId} creates a copy of this {@code TodoEntry} with the {@code id} set to the given value.
     *
     * @param id the new {@code id}
     * @return the created {@code TodoEntry}
     */
    public TodoEntry withId(int id) {
        return new TodoEntry(id, this.text, this.completed, this.hover, this.editMode);
    }


    /**
     * This is the getter of the text that is displayed.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * The method {@code withText} creates a copy of this {@code TodoEntry} with the {@code text} set to the given value.
     *
     * @param text the new {@code text}
     * @return the created {@code TodoEntry}
     * @throws NullPointerException if {@code text} is {@code null}
     */
    public TodoEntry withText(String text) {
        return new TodoEntry(this.id, text, this.completed, this.hover, this.editMode);
    }


    /**
     * This is the getter of the {@code completed}-flag, which signals if the todo-item has been completed already.
     *
     * @return the {@code completed}-flag
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * The method {@code withCompleted} creates a copy of this {@code TodoEntry} with the {@code completed}-flag set to
     * the given value.
     *
     * @param completed the new {@code completed}-flag
     * @return the created {@code TodoEntry}
     */
    public TodoEntry withCompleted(boolean completed) {
        return new TodoEntry(this.id, this.text, completed, this.hover, this.editMode);
    }


    /**
     * This is the getter of the {@code hover}-flag, which signals if the user is hovering over this todo-item.
     *
     * @return the {@code hover}-flag
     */
    public boolean isHover() {
        return hover;
    }

    /**
     * The method {@code withHover} creates a copy of this {@code TodoEntry} with the {@code hover}-flag set to
     * the given value.
     *
     * @param hover the new {@code hover}-flag
     * @return the created {@code TodoEntry}
     */
    public TodoEntry withHover(boolean hover) {
        return new TodoEntry(this.id, this.text, this.completed, hover, this.editMode);
    }


    /**
     * This is the getter of the {@code editMode}-flag, which signals if the todo-item is currently being edited.
     *
     * @return the {@code editMode}-flag
     */
    public boolean isEditMode() {
        return editMode;
    }

    /**
     * The method {@code withEditMode} creates a copy of this {@code TodoEntry} with the {@code editMode}-flag set to
     * the given value.
     *
     * @param editMode the new {@code editMode}-flag
     * @return the created {@code TodoEntry}
     */
    public TodoEntry withEditMode(boolean editMode) {
        return new TodoEntry(this.id, this.text, this.completed, this.hover, editMode);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("text", text)
                .append("completed", completed)
                .append("hover", hover)
                .toString();
    }
}
