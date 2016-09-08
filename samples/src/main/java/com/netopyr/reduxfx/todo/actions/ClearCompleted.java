package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClearCompleted implements Action {

    public ActionType getType() {
        return ActionType.CLEAR_COMPLETED;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
