package com.netopyr.reduxfx.differ.patches;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class Patch {

    private final int index;

    protected Patch(int index) {
        this.index = index;
    }

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
