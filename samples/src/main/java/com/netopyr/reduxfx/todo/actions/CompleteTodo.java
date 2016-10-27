package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class CompleteTodo implements Action {

    private final int id;

    CompleteTodo(int id) {
        this.id = id;
    }

    public ActionType getType() {
        return ActionType.COMPLETE_TODO;
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
