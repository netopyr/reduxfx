package com.netopyr.reduxfx.todo.state;

import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AppModel {

    private final String newTodoText;
    private final Seq<TodoEntry> todos;
    private final Filter filter;

    public AppModel(String newTodoText, Seq<TodoEntry> todos, Filter filter) {
        this.newTodoText = newTodoText;
        this.todos = todos;
        this.filter = filter;
    }

    public String getNewTodoText() {
        return newTodoText;
    }

    public Seq<TodoEntry> getTodos() {
        return todos;
    }

    public Filter getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("newTodoText", newTodoText)
                .append("todos", todos)
                .append("filter", filter)
                .toString();
    }
}
