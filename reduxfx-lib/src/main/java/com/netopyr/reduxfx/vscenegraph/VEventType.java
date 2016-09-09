package com.netopyr.reduxfx.vscenegraph;

public enum VEventType {
    ACTION("action");

    private final String name;

    VEventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
