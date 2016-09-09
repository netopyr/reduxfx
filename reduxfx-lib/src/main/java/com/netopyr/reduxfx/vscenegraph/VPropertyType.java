package com.netopyr.reduxfx.vscenegraph;

public enum VPropertyType {
    ITEMS("items");

    private final String name;

    VPropertyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
