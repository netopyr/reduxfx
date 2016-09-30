package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class EditToDo implements Action {

    private final int id;
    private final String text;

    EditToDo(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public ActionType getType() {
        return ActionType.EDIT_TODO;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("text", text)
                .toString();
    }
}
