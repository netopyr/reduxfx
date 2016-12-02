package com.netopyr.reduxfx.colorchooser.component.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An instance of the class {@code ColorChooserModel} is the root node of the state-tree of this component.
 *
 * In ReduxFX the whole state of a component is kept in a single, immutable data structure. This data structure is
 * created in the {@link com.netopyr.reduxfx.colorchooser.component.updater.ColorChooserUpdater}. The
 * {@code ColorChooserUpdater} gets the current state together with the
 * {@link com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserAction} that should be performed and calculates
 * the new state from that.
 *
 * The new state is passed to the {@link com.netopyr.reduxfx.colorchooser.component.view.ColorChooserView}-function,
 * which calculates the new virtual Scenegraph.
 */
public final class ColorChooserModel {

    private final int red;
    private final int green;
    private final int blue;

    /**
     * The default constructor creates a new instance of {@code ColorChooserModel} with all properties set to their
     * default values.
     *
     * Default values are: {red: 0, green: 0, blue: 0}
     */
    public ColorChooserModel() {
        this(0, 0, 0);
    }
    private ColorChooserModel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }



    /**
     * This is the getter of the red value.
     *
     * @return the value of red
     */
    public final int getRed() {
        return red;
    }

    /**
     * The method {@code withRed} creates a copy of this {@code ColorChooserModel} with the {@code red}-value set to
     * the given value.
     *
     * @param newRed the new {@code red}-value
     * @return the created {@code ColorChooserModel}
     */
    public final ColorChooserModel withRed(int newRed) {
        return new ColorChooserModel(newRed, green, blue);
    }



    /**
     * This is the getter of the green value.
     *
     * @return the value of green
     */
    public final int getGreen() {
        return green;
    }

    /**
     * The method {@code withGreen} creates a copy of this {@code ColorChooserModel} with the {@code green}-value set to
     * the given value.
     *
     * @param newGreen the new {@code green}-value
     * @return the created {@code ColorChooserModel}
     */
    public final ColorChooserModel withGreen(int newGreen) {
        return new ColorChooserModel(red, newGreen, blue);
    }



    /**
     * This is the getter of the blue value.
     *
     * @return the value of blue
     */
    public final int getBlue() {
        return blue;
    }

    /**
     * The method {@code withBlue} creates a copy of this {@code ColorChooserModel} with the {@code blue}-value set to
     * the given value.
     *
     * @param newBlue the new {@code blue}-value
     * @return the created {@code ColorChooserModel}
     */
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
