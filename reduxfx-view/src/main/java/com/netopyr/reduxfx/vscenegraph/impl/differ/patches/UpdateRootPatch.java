package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import io.vavr.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("rootNode", rootNode)
                .toString();
    }
}
