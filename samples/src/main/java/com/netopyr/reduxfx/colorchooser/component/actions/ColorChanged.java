package com.netopyr.reduxfx.colorchooser.component.actions;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ColorChanged implements ColorChooserAction {

    private final Color value;

    ColorChanged(Color value) {
        this.value = value;
    }

    public Color getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
