package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SetEditMode implements Action {

    private final int id;
    private final boolean value;

    SetEditMode(int id, boolean value) {
        this.id = id;
        this.value = value;
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
