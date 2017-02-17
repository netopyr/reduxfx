package com.netopyr.reduxfx.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class UpdateRootPatch extends Patch {

    private final VNode newNode;

    public UpdateRootPatch(Vector<Object> path, VNode newNode) {
        super(path);
        this.newNode = Objects.requireNonNull(newNode, "NewNode must not be null");
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
