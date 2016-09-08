package com.netopyr.reduxfx.todo.actions;

public class Actions {

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

    public static Action clompleteAll() {
        return new CompleteAll();
    }

    public static Action clearCompleted() {
        return new ClearCompleted();
    }

    public static Action newTextFieldChanged(String text) {
        return new NewTextFieldChanged(text);
    }
}
