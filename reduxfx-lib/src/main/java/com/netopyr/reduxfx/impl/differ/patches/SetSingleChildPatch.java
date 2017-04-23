package com.netopyr.reduxfx.impl.differ.patches;

import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class SetSingleChildPatch extends Patch {

    private final String name;
    private final Option<VNode> child;

    public SetSingleChildPatch(Vector<Object> path, String name, Option<VNode> child) {
        super(path);
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.child = Objects.requireNonNull(child, "Child must not be null");
    }

    public String getName() {
        return name;
    }

    public Option<VNode> getChild() {
        return child;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("child", child)
                .toString();
    }
}
