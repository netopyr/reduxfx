package com.netopyr.reduxfx.colorchooser.app.actions;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class UpdateColor implements Action {

    private final Color value;

    UpdateColor(Color value) {
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
