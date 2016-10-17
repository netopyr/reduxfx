package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Actions {

    private static final Logger LOG = LoggerFactory.getLogger(Actions.class);

    private Actions() {}

    public static Action addToDo() {
        return new AddToDo();
    }

    public static Action deleteToDo(int id) {
        return new DeleteToDo(id);
    }

    public static Action editToDo(int id, String text) {
        return new EditToDo(id, text);
    }

    public static Action completeToDo(int id) {
        return new CompleteToDo(id);
    }

    public static Action completeAll() {
        return new CompleteAll();
    }

    public static Action clearCompleted() {
        return new ClearCompleted();
    }

    public static Action newTextFieldChanged(String text) {
        return new NewTextFieldChanged(text);
    }

    public static Action setFilter(Filter filter) {
        return new SetFilter(filter);
    }

    public static Action setToDoHover(int id, boolean value) {
        return new SetToDoHover(id, value);
    }
}
