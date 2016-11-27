package com.netopyr.reduxfx.todo.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An instance of the class {@code TodoEntry} contains all state of a single entry in the todo-list.
 */
public final class TodoEntry {

    private final int id;
    private final String text;
    private final boolean completed;
    private final boolean hover;
    private final boolean editMode;

    public TodoEntry(int id, String text, boolean completed, boolean hover, boolean editMode) {
        this.id = id;
        this.text = text;
        this.completed = completed;
        this.hover = hover;
        this.editMode = editMode;
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
     * This is the getter of the text that is displayed.
     *
     * @return the text
     */
    public String getText() {
        return text;
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
     * This is the getter of the {@code hover}-flag, which signals if the user is hovering over this todo-item.
     *
     * @return the {@code hover}-flag
     */
    public boolean isHover() {
        return hover;
    }

    /**
     * This is the getter of the {@code editMode}-flag, which signals if the todo-item is currently being edited.
     *
     * @return the {@code editMode}-flag
     */
    public boolean isEditMode() {
        return editMode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("text", text)
                .append("completed", completed)
                .append("hover", hover)
                .toString();
    }
}
