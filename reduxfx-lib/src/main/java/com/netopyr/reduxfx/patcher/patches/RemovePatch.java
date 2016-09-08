package com.netopyr.reduxfx.patcher.patches;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RemovePatch extends Patch {

    public RemovePatch(int index) {
        super(index);
    }

    @Override
    public Type getType() {
        return Type.REMOVE;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
