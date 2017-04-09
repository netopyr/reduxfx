package com.netopyr.reduxfx.vscenegraph.property;

import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class VProperty {

    private final boolean isValueDefined;
    private final Object value;
    private final Option<VChangeListener> changeListener;
    private final Option<VInvalidationListener> invalidationListener;

    public VProperty(Object value,
                     Option<VChangeListener> changeListener,
                     Option<VInvalidationListener> invalidationListener) {
        this.isValueDefined = true;
        this.value = value;
        this.changeListener = changeListener;
        this.invalidationListener = invalidationListener;
    }

    @SuppressWarnings("unchecked")
    public VProperty(Option<VChangeListener> changeListener,
                     Option<VInvalidationListener> invalidationListener) {
        this.isValueDefined = false;
        this.value = null;
        this.changeListener = changeListener;
        this.invalidationListener = invalidationListener;
    }

    public boolean isValueDefined() {
        return isValueDefined;
    }

    public Object getValue() {
        if (! isValueDefined) {
            throw new IllegalStateException("No value defined");
        }
        return value;
    }

    public Option<VChangeListener> getChangeListener() {
        return changeListener;
    }

    public Option<VInvalidationListener> getInvalidationListener() {
        return invalidationListener;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("isValueDefined", isValueDefined)
                .append("value", value)
                .append("changeListener", changeListener.stringPrefix())
                .append("invalidationListener", invalidationListener.stringPrefix())
                .toString();
    }
}
