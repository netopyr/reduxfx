package com.netopyr.reduxfx.colorchooser.component.actions;

public interface ColorChooserAction {

    enum ActionType {
        UPDATE_RED,
        UPDATE_GREEN,
        UPDATE_BLUE
    }

    ActionType getType();
}
