package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class NewTextFieldChanged implements Action {

    private final String text;

    NewTextFieldChanged(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("text", text)
                .toString();
    }
}
