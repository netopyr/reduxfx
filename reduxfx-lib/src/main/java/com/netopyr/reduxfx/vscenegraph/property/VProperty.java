package com.netopyr.reduxfx.vscenegraph.property;

import com.netopyr.reduxfx.vscenegraph.VElement;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VProperty<TYPE, ACTION> implements VElement<ACTION> {
    
    private final VPropertyType type;
    private final TYPE value;
    private final Option<VChangeListener<? super TYPE, ACTION>> changeListener;
    private final Option<VInvalidationListener<ACTION>> invalidationListener;

    public VProperty(VPropertyType type,
                     TYPE value,
                     Option<VChangeListener<? super TYPE, ACTION>> changeListener,
                     Option<VInvalidationListener<ACTION>> invalidationListener) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.value = value;
        this.changeListener = changeListener;
        this.invalidationListener = invalidationListener;
    }

    public VPropertyType getType() {
        return type;
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
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("value", value)
                .append("changeListener", changeListener.stringPrefix())
                .append("invalidationListener", invalidationListener.stringPrefix())
                .toString();
    }
}
