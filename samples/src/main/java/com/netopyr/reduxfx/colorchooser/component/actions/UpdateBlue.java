package com.netopyr.reduxfx.colorchooser.component.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UpdateBlue implements ColorChooserAction {

    private final double value;

    UpdateBlue(double value) {
        this.value = value;
    }

    @Override
    public ActionType getType() {
        return ActionType.UPDATE_BLUE;
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
