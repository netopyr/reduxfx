package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import io.vavr.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class RemovePatch extends Patch {

    private final int startRange;
    private final int endRange;

    public RemovePatch(Vector<Object> path, int startRange, int endRange) {
        super(path);
        this.startRange = startRange;
        this.endRange = endRange;
    }

    public int getStartRange() {
        return startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("startRange", startRange)
                .append("endRange", endRange)
                .toString();
    }
}
