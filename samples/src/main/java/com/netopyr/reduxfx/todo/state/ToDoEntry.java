package com.netopyr.reduxfx.todo.state;

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
        final StringBuilder sb = new StringBuilder("ToDoEntry{");
        sb.append("id=").append(id);
        sb.append(", text='").append(text).append('\'');
        sb.append(", completed=").append(completed);
        sb.append('}');
        return sb.toString();
    }
}
