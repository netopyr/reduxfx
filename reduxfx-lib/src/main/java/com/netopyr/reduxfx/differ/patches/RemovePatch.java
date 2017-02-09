package com.netopyr.reduxfx.differ.patches;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RemovePatch extends Patch {

    public RemovePatch(int index) {
        super(index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
