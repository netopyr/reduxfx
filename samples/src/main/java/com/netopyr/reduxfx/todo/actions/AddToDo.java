package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddToDo implements Action {

    private final String text;

    AddToDo(String text) {
        this.text = text;
    }

    public ActionType getType() {
        return ActionType.ADD_TODO;
    }

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
