package com.netopyr.reduxfx.vscenegraph;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;
import java.util.function.Consumer;

public class VReference implements VElement {

    private final Consumer<Object> ref;

    public VReference(Consumer<Object> ref) {
        this.ref = Objects.requireNonNull(ref, "Ref must not be null");
    }

    public Consumer<Object> getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
