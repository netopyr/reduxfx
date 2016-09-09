package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeleteToDo implements Action {

    private final int id;

    DeleteToDo(int id) {
        this.id = id;
    }

    public ActionType getType() {
        return ActionType.DELETE_TODO;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
