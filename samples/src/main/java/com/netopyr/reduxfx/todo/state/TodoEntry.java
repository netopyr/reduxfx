package com.netopyr.reduxfx.todo.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isHover() {
        return hover;
    }

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
