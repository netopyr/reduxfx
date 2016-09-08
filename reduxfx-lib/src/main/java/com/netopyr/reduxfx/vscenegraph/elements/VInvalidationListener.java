package com.netopyr.reduxfx.vscenegraph.elements;

import javafx.beans.InvalidationListener;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VInvalidationListener implements VElement {

    private final String name;
    private final InvalidationListener listener;

    public VInvalidationListener(String name, InvalidationListener listener) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.listener = Objects.requireNonNull(listener, "Listener must not be null");
    }

    public String getName() {
        return name;
    }

    public InvalidationListener getListener() {
        return listener;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
