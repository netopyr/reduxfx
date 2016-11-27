package com.netopyr.reduxfx.colorchooser.component.actions;

import javafx.scene.paint.Color;

public final class ColorChooserActions {

    private ColorChooserActions() {}

    public static ColorChooserAction updateRed(int value) {
        return new UpdateRed(value);
    }

    public static ColorChooserAction updateGreen(int value) {
        return new UpdateGreen(value);
    }

    public static ColorChooserAction updateBlue(int value) {
        return new UpdateBlue(value);
    }

    public static ColorChooserAction colorChanged(Color value) {
        return new ColorChanged(value);
    }
}
