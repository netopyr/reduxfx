package com.netopyr.reduxfx.vscenegraph;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VProperty implements VElement {
    
    private final VPropertyType type;
    private final Object value;

    public VProperty(VPropertyType type, Object value) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.value = value;
    }

    public VPropertyType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("value", value)
                .toString();
    }
}
