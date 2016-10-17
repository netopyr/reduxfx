package com.netopyr.reduxfx.vscenegraph.property;

import com.netopyr.reduxfx.vscenegraph.VElement;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VProperty<TYPE, ACTION> implements VElement<ACTION> {
    
    private final String name;
    private final TYPE value;
    private final Option<VChangeListener<? super TYPE, ACTION>> changeListener;
    private final Option<VInvalidationListener<ACTION>> invalidationListener;

    public VProperty(String name,
                     TYPE value,
                     Option<VChangeListener<? super TYPE, ACTION>> changeListener,
                     Option<VInvalidationListener<ACTION>> invalidationListener) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.value = value;
        this.changeListener = changeListener;
        this.invalidationListener = invalidationListener;
    }

    public String getName() {
        return name;
    }

    public TYPE getValue() {
        return value;
    }

    public Option<VChangeListener<? super TYPE, ACTION>> getChangeListener() {
        return changeListener;
    }

    public Option<VInvalidationListener<ACTION>> getInvalidationListener() {
        return invalidationListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VProperty<?, ?> vProperty = (VProperty<?, ?>) o;

        return new EqualsBuilder()
                .append(name, vProperty.name)
                .append(value, vProperty.value)
                .append(changeListener, vProperty.changeListener)
                .append(invalidationListener, vProperty.invalidationListener)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(value)
                .append(changeListener)
                .append(invalidationListener)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("value", value)
                .append("changeListener", changeListener.stringPrefix())
                .append("invalidationListener", invalidationListener.stringPrefix())
                .toString();
    }
}
