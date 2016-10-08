package com.netopyr.reduxfx.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class InsertPatch extends Patch {

    private final VNode newNode;

    public InsertPatch(int index, VNode newNode) {
        super(index);
        this.newNode = newNode;
    }

    @Override
    public Type getType() {
        return Type.INSERT;
    }

    public VNode getNewNode() {
        return newNode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("newNode", newNode)
                .toString();
    }
}
