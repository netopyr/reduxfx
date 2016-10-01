package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AddToDo implements Action {

    public ActionType getType() {
        return ActionType.ADD_TODO;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
