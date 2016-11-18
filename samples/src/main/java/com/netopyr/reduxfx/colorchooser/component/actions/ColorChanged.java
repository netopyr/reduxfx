package com.netopyr.reduxfx.colorchooser.component.actions;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColorChanged implements ColorChooserAction {

    private final Color value;

    public ColorChanged(Color value) {
        this.value = value;
    }

    @Override
    public ActionType getType() {
        return ActionType.COLOR_CHANGED;
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
