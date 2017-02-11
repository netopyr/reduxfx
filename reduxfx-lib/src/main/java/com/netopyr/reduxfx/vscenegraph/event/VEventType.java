package com.netopyr.reduxfx.vscenegraph.event;

public enum VEventType {
    ACTION("action"),
    CLOSE_REQUEST("closeRequest"),
    MOUSE_CLICKED("mouseClicked");

    private final String name;

    VEventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
