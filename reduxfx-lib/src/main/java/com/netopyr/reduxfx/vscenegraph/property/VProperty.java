package com.netopyr.reduxfx.vscenegraph.property;

import com.netopyr.reduxfx.vscenegraph.VElement;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VProperty<TYPE, ACTION> implements VElement<ACTION> {

    private static final Object NO_VALUE = new Object();
    @SuppressWarnings("unchecked")
    public static <TYPE> TYPE getNoValue() { return (TYPE) NO_VALUE; }


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

    @SuppressWarnings("unchecked")
    public VProperty(String name,
                     Option<VChangeListener<? super TYPE, ACTION>> changeListener,
                     Option<VInvalidationListener<ACTION>> invalidationListener) {
        this(name, getNoValue(), changeListener, invalidationListener);
    }

    public String getName() {
        return name;
    }

    public boolean isValueDefined() {
        return NO_VALUE != value;
    }

    public TYPE getValue() {
        if (! isValueDefined()) {
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
                .append("value", value)
                .append("changeListener", changeListener.stringPrefix())
                .append("invalidationListener", invalidationListener.stringPrefix())
                .toString();
    }
}
