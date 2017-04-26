package com.netopyr.reduxfx.component.command;

import com.netopyr.reduxfx.updater.Command;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class ObjectChangedCommand<T> implements Command {

    private final String propertyName;
    private final T newValue;

    public ObjectChangedCommand(String propertyName, T newValue) {
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public T getNewValue() {
        return newValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("propertyName", propertyName)
                .append("newValue", newValue)
                .toString();
    }
}
