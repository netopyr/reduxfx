package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import io.vavr.collection.Array;
import io.vavr.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class SetChildrenPatch extends Patch {

    private final String name;
    private final Array<VNode> children;

    public SetChildrenPatch(Vector<Object> path, String name, Array<VNode> children) {
        super(path);
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.children = Objects.requireNonNull(children, "Children must not be null");
    }

    public String getName() {
        return name;
    }

    public Array<VNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("children", children)
                .toString();
    }
}
