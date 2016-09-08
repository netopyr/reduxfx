package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CompleteAll implements Action {

    public ActionType getType() {
        return ActionType.COMPLETE_ALL;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
