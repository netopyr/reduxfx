package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ClearCompleted implements Action {

    ClearCompleted() {}

    public ActionType getType() {
        return ActionType.CLEAR_COMPLETED;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
