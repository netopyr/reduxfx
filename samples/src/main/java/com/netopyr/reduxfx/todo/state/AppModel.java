package com.netopyr.reduxfx.todo.state;

import javaslang.collection.Array;
import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 *
 * In ReduxFX the whole application state is kept in a single data structure. The
 * {@link com.netopyr.reduxfx.todo.updater.Updater} keeps a reference to the state and passed to the {@link com.netopyr.reduxfx.colorchooser.app.view.MainView}
 *
 */
public final class AppModel {

    private final String newTodoText;
    private final Seq<TodoEntry> todos;
    private final Filter filter;

    public AppModel() {
        this("", Array.empty(), Filter.ALL);
    }
    private AppModel(String newTodoText, Seq<TodoEntry> todos, Filter filter) {
        this.newTodoText = newTodoText;
        this.todos = todos;
        this.filter = filter;
    }

    public String getNewTodoText() {
        return newTodoText;
    }

    public AppModel withNewTodoText(String newNewTodoText) {
        return new AppModel(newNewTodoText, this.todos, this.filter);
    }

    public Seq<TodoEntry> getTodos() {
        return todos;
    }

    public AppModel withTodos(Seq<TodoEntry> newTodos) {
        return new AppModel(this.newTodoText, newTodos, this.filter);
    }

    public Filter getFilter() {
        return filter;
    }

    public AppModel withFilter(Filter newFilter) {
        return new AppModel(this.newTodoText, this.todos, newFilter);
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
