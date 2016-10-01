package com.netopyr.reduxfx.todo.state;

import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AppModel {

    private final String newToDoText;
    private final Seq<ToDoEntry> todos;
    private final Filter filter;

    public AppModel(String newToDoText, Seq<ToDoEntry> todos, Filter filter) {
        this.newToDoText = newToDoText;
        this.todos = todos;
        this.filter = filter;
    }

    public String getNewToDoText() {
        return newToDoText;
    }

    public Seq<ToDoEntry> getTodos() {
        return todos;
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("newToDoText", newToDoText)
                .append("todos", todos)
                .append("filter", filter)
                .toString();
    }
}
