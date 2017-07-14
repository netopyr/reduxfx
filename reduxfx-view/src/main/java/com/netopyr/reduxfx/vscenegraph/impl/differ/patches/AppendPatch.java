package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import io.vavr.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class AppendPatch extends Patch {

    private final VNode newNode;

    public AppendPatch(Vector<Object> path, VNode newNode) {
        super(path);
        this.newNode = Objects.requireNonNull(newNode, "NewNode must not be null");
    }

    public VNode getNewNode() {
        return newNode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("newNode", newNode)
                .toString();
    }
}
