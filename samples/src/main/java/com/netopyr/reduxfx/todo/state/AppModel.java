package com.netopyr.reduxfx.todo.state;

import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AppModel {

    private final Seq<ToDoEntry> todos;

    @SuppressWarnings("unchecked")
    public AppModel(Seq<ToDoEntry> todos) {
        this.todos = todos;
    }

    public Seq<ToDoEntry> getTodos() {
        return todos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("todos", todos)
                .toString();
    }
}
