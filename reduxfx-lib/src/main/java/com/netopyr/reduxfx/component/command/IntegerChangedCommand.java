package com.netopyr.reduxfx.component.command;

import com.netopyr.reduxfx.updater.Command;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class IntegerChangedCommand implements Command {

    private final String propertyName;
    private final int newValue;

    public IntegerChangedCommand(String propertyName, int newValue) {
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public int getNewValue() {
        return newValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("propertyName", propertyName)
                .append("newValue", newValue)
                .toString();
    }
}
