package com.netopyr.reduxfx.colorchooser.component.actions;

public final class ColorChooserActions {

    private ColorChooserActions() {}

    public static ColorChooserAction updateRed(double value) {
        return new UpdateRed(value);
    }

    public static ColorChooserAction updateGreen(double value) {
        return new UpdateGreen(value);
    }

    public static ColorChooserAction updateBlue(double value) {
        return new UpdateBlue(value);
    }
}
