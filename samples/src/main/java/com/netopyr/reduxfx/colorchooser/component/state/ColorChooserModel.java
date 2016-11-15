package com.netopyr.reduxfx.colorchooser.component.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class ColorChooserModel {

    private final double red;
    private final double green;
    private final double blue;

    public ColorChooserModel() {
        this(0.0, 0.0, 0.0);
    }
    private ColorChooserModel(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public final double getRed() {
        return red;
    }

    public final ColorChooserModel withRed(double newRed) {
        return new ColorChooserModel(newRed, green, blue);
    }

    public final double getGreen() {
        return green;
    }

    public final ColorChooserModel withGreen(double newGreen) {
        return new ColorChooserModel(red, newGreen, blue);
    }

    public final double getBlue() {
        return blue;
    }

    public final ColorChooserModel withBlue(double newBlue) {
        return new ColorChooserModel(red, green, newBlue);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("red", red)
                .append("green", green)
                .append("blue", blue)
                .toString();
    }
}
