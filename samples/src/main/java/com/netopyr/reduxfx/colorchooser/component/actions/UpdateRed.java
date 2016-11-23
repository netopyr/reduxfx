package com.netopyr.reduxfx.colorchooser.component.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class UpdateRed implements ColorChooserAction {

    private final double value;

    UpdateRed(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
