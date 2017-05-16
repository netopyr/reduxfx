package com.netopyr.reduxfx.examples.colorchooser.component.state;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import com.netopyr.reduxfx.examples.colorchooser.component.view.ColorChooserView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An instance of the class {@code ColorChooserState} is the root node of the state-tree of this component.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link ColorChooserUpdater}. The {@code ColorChooserUpdater} gets the current state together with the action
 * that should be performed and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link ColorChooserView}-function, which calculates the new virtual Scenegraph.
 */
public final class ColorChooserState {

    private final int red;
    private final int green;
    private final int blue;

    /**
     * The default constructor creates a new instance of {@code ColorChooserState} with all properties set to their
     * default values.
     * <p>
     * Default values are: {red: 0, green: 0, blue: 0}
     */
    public ColorChooserState() {
        this(0, 0, 0);
    }

    private ColorChooserState(int red, int green, int blue) {
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
     * The method {@code withRed} creates a copy of this {@code ColorChooserState} with the {@code red}-value set to
     * the given value.
     *
     * @param red the new {@code red}-value
     * @return the created {@code ColorChooserState}
     */
    public final ColorChooserState withRed(int red) {
        return new ColorChooserState(red, green, blue);
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
     * The method {@code withGreen} creates a copy of this {@code ColorChooserState} with the {@code green}-value set to
     * the given value.
     *
     * @param green the new {@code green}-value
     * @return the created {@code ColorChooserState}
     */
    public final ColorChooserState withGreen(int green) {
        return new ColorChooserState(red, green, blue);
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
     * The method {@code withBlue} creates a copy of this {@code ColorChooserState} with the {@code blue}-value set to
     * the given value.
     *
     * @param blue the new {@code blue}-value
     * @return the created {@code ColorChooserState}
     */
    public final ColorChooserState withBlue(int blue) {
        return new ColorChooserState(red, green, blue);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("color", String.format("0x%02x%02x%02x", red, green, blue))
                .toString();
    }
}
