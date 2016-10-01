package com.netopyr.reduxfx.vscenegraph;

public enum VPropertyType {
    PADDING("padding"),
    SPACING("spacing"),
    TEXT("text"),
    DISABLE("disable"),
    ITEMS("items"),
    TOGGLE_GROUP("toggleGroup");

    private final String name;

    VPropertyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
