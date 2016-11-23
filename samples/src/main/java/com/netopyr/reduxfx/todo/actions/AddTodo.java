package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AddTodo implements Action {

    AddTodo() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
