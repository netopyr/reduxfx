package com.netopyr.reduxfx.vscenegraph;

import javafx.scene.Node;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;
import java.util.function.Consumer;

public class VReference implements VElement {

    private final Consumer<? super Node> ref;

    public VReference(Consumer<? super Node> ref) {
        this.ref = Objects.requireNonNull(ref, "Ref must not be null");
    }

    public Consumer<? super Node> getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
