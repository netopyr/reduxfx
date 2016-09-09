package com.netopyr.reduxfx.todo.actions;

public interface Action {

    enum ActionType { ADD_TODO, DELETE_TODO, EDIT_TODO, COMPLETE_TODO, COMPLETE_ALL, CLEAR_COMPLETED }

    ActionType getType();
}
