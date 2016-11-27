package com.netopyr.reduxfx.colorchooser.component.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class UpdateBlue implements ColorChooserAction {

    private final int value;

    UpdateBlue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
