package com.netopyr.reduxfx.differ.patches;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class Patch {

    public enum Type { REPLACED, ATTRIBUTES, ORDER, INSERT, REMOVE }

    private final int index;

    protected Patch(int index) {
        this.index = index;
    }

    public abstract Type getType();

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("index", index)
                .toString();
    }
}
