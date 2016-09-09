package com.netopyr.reduxfx.vscenegraph;

import javafx.beans.value.ChangeListener;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VChangeListener<T> implements VElement {

    private final VPropertyType type;
    private final ChangeListener<T> listener;

    public VChangeListener(VPropertyType type, ChangeListener<T> listener) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.listener = Objects.requireNonNull(listener, "Listener must not be null");
    }

    public VPropertyType getType() {
        return type;
    }

    public ChangeListener<T> getListener() {
        return listener;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .toString();
    }
}
