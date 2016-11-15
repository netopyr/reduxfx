package com.netopyr.reduxfx.colorchooser.component.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UpdateGreen implements ColorChooserAction {

    private final double value;

    UpdateGreen(double value) {
        this.value = value;
    }

    @Override
    public ActionType getType() {
        return ActionType.UPDATE_GREEN;
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
