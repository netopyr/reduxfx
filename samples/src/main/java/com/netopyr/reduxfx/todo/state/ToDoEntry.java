package com.netopyr.reduxfx.todo.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ToDoEntry {

    private final int id;
    private final String text;
    private final boolean completed;

    public ToDoEntry(int id, String text, boolean completed) {
        this.id = id;
        this.text = text;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("text", text)
                .append("completed", completed)
                .toString();
    }
}
