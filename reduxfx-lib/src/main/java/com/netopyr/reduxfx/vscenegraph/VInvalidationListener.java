package com.netopyr.reduxfx.vscenegraph;

import javafx.beans.InvalidationListener;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VInvalidationListener implements VElement {

    private final VPropertyType type;
    private final InvalidationListener listener;

    public VInvalidationListener(VPropertyType type, InvalidationListener listener) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.listener = Objects.requireNonNull(listener, "Listener must not be null");
    }

    public VPropertyType getType() {
        return type;
    }

    public InvalidationListener getListener() {
        return listener;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .toString();
    }
}
