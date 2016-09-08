package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NewTextFieldChanged implements Action {

    private final String text;

    public NewTextFieldChanged(String text) {
        this.text = text;
    }

    @Override
    public ActionType getType() {
        return ActionType.NEW_TEXTFIELD_CHANGED;
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
