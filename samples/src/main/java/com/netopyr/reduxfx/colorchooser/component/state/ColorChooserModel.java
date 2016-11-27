package com.netopyr.reduxfx.colorchooser.component.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ColorChooserModel {

    private final int red;
    private final int green;
    private final int blue;

    public ColorChooserModel() {
        this(0, 0, 0);
    }
    private ColorChooserModel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public final int getRed() {
        return red;
    }

    public final ColorChooserModel withRed(int newRed) {
        return new ColorChooserModel(newRed, green, blue);
    }

    public final int getGreen() {
        return green;
    }

    public final ColorChooserModel withGreen(int newGreen) {
        return new ColorChooserModel(red, newGreen, blue);
    }

    public final int getBlue() {
        return blue;
    }

    public final ColorChooserModel withBlue(int newBlue) {
        return new ColorChooserModel(red, green, newBlue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("color", String.format("0x%02x%02x%02x", red, green, blue))
                .toString();
    }
}
