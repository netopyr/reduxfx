package com.netopyr.reduxfx.vscenegraph.impl.differ.patches;

import io.vavr.collection.Vector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public abstract class Patch {

    private final Vector<Object> path;

    protected Patch(Vector<Object> path) {
        this.path = Objects.requireNonNull(path, "Path must not be null");
    }

    public Vector<Object> getPath() {
        return path;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("path", path)
                .toString();
    }
}
