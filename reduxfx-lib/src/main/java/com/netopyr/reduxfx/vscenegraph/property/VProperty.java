package com.netopyr.reduxfx.vscenegraph.property;

import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VProperty<TYPE, ACTION> {

    public VProperty(String name, TYPE value) {
        this(name, value, Option.none(), Option.none());
    }


    private final String name;
    private final boolean isValueDefined;
    private final TYPE value;
    private final Option<VChangeListener<? super TYPE, ACTION>> changeListener;
    private final Option<VInvalidationListener<ACTION>> invalidationListener;

    public VProperty(String name,
                     TYPE value,
                     Option<VChangeListener<? super TYPE, ACTION>> changeListener,
                     Option<VInvalidationListener<ACTION>> invalidationListener) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.isValueDefined = true;
        this.value = value;
        this.changeListener = changeListener;
        this.invalidationListener = invalidationListener;
    }

    @SuppressWarnings("unchecked")
    public VProperty(String name,
                     Option<VChangeListener<? super TYPE, ACTION>> changeListener,
                     Option<VInvalidationListener<ACTION>> invalidationListener) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.isValueDefined = false;
        this.value = null;
        this.changeListener = changeListener;
        this.invalidationListener = invalidationListener;
    }

    public String getName() {
        return name;
    }

    public boolean isValueDefined() {
        return isValueDefined;
    }

    public TYPE getValue() {
        if (! isValueDefined) {
            throw new IllegalStateException("No value defined");
        }
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
                .append("name", name)
                .append("isValueDefined", isValueDefined)
                .append("value", value)
                .append("changeListener", changeListener.stringPrefix())
                .append("invalidationListener", invalidationListener.stringPrefix())
                .toString();
    }
}
