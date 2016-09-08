package com.netopyr.reduxfx.vscenegraph.elements;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VProperty implements VElement {
    
    private final String name;
    private final Object value;

    public VProperty(String name, Object value) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("value", value)
                .toString();
    }
}
