package com.netopyr.reduxfx.colorchooser.app.actions;

public interface Action {

    enum ActionType {
        UPDATE_COLOR
    }

    ActionType getType();
}
