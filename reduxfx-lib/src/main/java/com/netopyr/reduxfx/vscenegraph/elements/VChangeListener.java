package com.netopyr.reduxfx.vscenegraph.elements;

import javafx.beans.value.ChangeListener;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VChangeListener<T> implements VElement {

    private final String name;
    private final ChangeListener<T> listener;

    public VChangeListener(String name, ChangeListener<T> listener) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.listener = Objects.requireNonNull(listener, "Listener must not be null");
    }

    public String getName() {
        return name;
    }

    public ChangeListener<T> getListener() {
        return listener;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
