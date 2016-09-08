package com.netopyr.reduxfx.todo.state;

import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AppModel {

    private final String newToDoText;
    private final Seq<ToDoEntry> todos;

    @SuppressWarnings("unchecked")
    public AppModel(String newToDoText, Seq<ToDoEntry> todos) {
        this.newToDoText = newToDoText;
        this.todos = todos;
    }

    public String getNewToDoText() {
        return newToDoText;
    }

    public Seq<ToDoEntry> getTodos() {
        return todos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("newToDoText", newToDoText)
                .append("todos", todos)
                .toString();
    }
}
