package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Actions {

    private static final Logger LOG = LoggerFactory.getLogger(Actions.class);

    private Actions() {}

    public static Action addTodo() {
        return new AddTodo();
    }

    public static Action deleteTodo(int id) {
        return new DeleteTodo(id);
    }

    public static Action editTodo(int id, String text) {
        return new EditTodo(id, text);
    }

    public static Action completeTodo(int id) {
        return new CompleteTodo(id);
    }

    public static Action completeAll() {
        return new CompleteAll();
    }

    public static Action newTextFieldChanged(String text) {
        return new NewTextFieldChanged(text);
    }

    public static Action setFilter(Filter filter) {
        return new SetFilter(filter);
    }

    public static Action setTodoHover(int id, boolean value) {
        return new SetTodoHover(id, value);
    }

    public static Action setEditMode(int id, boolean value) {
        return new SetEditMode(id, value);
    }
}
