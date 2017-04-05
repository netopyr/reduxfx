package com.netopyr.reduxfx.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class UpdateRootPatch extends Patch {

    private final VNode rootNode;

    public UpdateRootPatch(VNode rootNode) {
        super(Vector.empty());
        this.rootNode = Objects.requireNonNull(rootNode, "RootNode must not be null");
    }

    public VNode getRootNode() {
        return rootNode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("rootNode", rootNode)
                .toString();
    }
}
