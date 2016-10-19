package com.netopyr.reduxfx.todo.actions;

public interface Action {

    enum ActionType {
        ADD_TODO,
        NEW_TEXTFIELD_CHANGED,
        DELETE_TODO,
        EDIT_TODO,
        COMPLETE_TODO,
        COMPLETE_ALL,
        CLEAR_COMPLETED,
        SET_FILTER,
        SET_EDIT_MODE,
        SET_TODO_HOVER
    }

    ActionType getType();
}
