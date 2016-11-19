package com.netopyr.reduxfx.patcher.property;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class PropertyKey {

    private final Class<?> nodeClass;
    private final String name;

    public PropertyKey(Class<?> nodeClass, String name) {
        this.nodeClass = nodeClass;
        this.name = name;
    }

    public Class<?> getNodeClass() {
        return nodeClass;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PropertyKey that = (PropertyKey) o;

        return new EqualsBuilder()
                .append(nodeClass, that.nodeClass)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(nodeClass)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nodeClass", nodeClass)
                .append("name", name)
                .toString();
    }
}
