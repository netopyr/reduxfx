package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import javaslang.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RemovePatch extends Patch {

    private final int index;

    public RemovePatch(Vector<Object> path, int index) {
        super(path);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("index", index)
                .toString();
    }
}
