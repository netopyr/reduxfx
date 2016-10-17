package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SetEditMode implements Action {

    private final int id;
    private final boolean value;

    public SetEditMode(int id, boolean value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public ActionType getType() {
        return ActionType.SET_EDIT_MODE;
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
