package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetTodoHover implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(SetTodoHover.class);

    private final int id;
    private final boolean value;

    public SetTodoHover(int id, boolean value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public ActionType getType() {
        return ActionType.SET_TODO_HOVER;
    }

    public int getId() {
        return id;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("value", value)
                .toString();
    }
}
