package com.netopyr.reduxfx.differ.patches;

import javaslang.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RemovePatch extends Patch {

    public RemovePatch(Vector<Object> path) {
        super(path);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
