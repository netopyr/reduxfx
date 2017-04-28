package com.netopyr.reduxfx.examples.colorchooser.component.actions;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import javafx.scene.paint.Color;

/**
 * The class {@code ColorChooserActions} contains factory-methods for all {@link ColorChooserAction}s that are
 * available in this application.
 */
public final class ColorChooserActions {

    private ColorChooserActions() {}



    /**
     * This method generates an {@link UpdateRedAction}.
     *
     * This {@link ColorChooserAction} is passed to the
     * {@link ColorChooserUpdater} when the slider for red
     * has changed.
     *
     * @param value the new value for red
     * @return the {@code UpdateRedAction}
     */
    public static ColorChooserAction updateRed(int value) {
        return new UpdateRedAction(value);
    }



    /**
     * This method generates an {@link UpdateGreenAction}.
     *
     * This {@link ColorChooserAction} is passed to the
     * {@link ColorChooserUpdater} when the slider for green
     * has changed.
     *
     * @param value the new value for green
     * @return the {@code UpdateGreenAction}
     */
    public static ColorChooserAction updateGreen(int value) {
        return new UpdateGreenAction(value);
    }



    /**
     * This method generates an {@link UpdateBlueAction}.
     *
     * This {@link ColorChooserAction} is passed to the
     * {@link ColorChooserUpdater} when the slider for blue
     * has changed.
     *
     * @param value the new value for blue
     * @return the {@code UpdateBlueAction}
     */
    public static ColorChooserAction updateBlue(int value) {
        return new UpdateBlueAction(value);
    }



    /**
     * This method generates an {@link ColorChooserAction}.
     *
     * This {@link ColorChooserAction} is passed to the
     * {@link ColorChooserUpdater} when the value of the color
     * property of this component was changed from outside.
     *
     * @param newColor the new color
     * @return the {@code ColorChangedAction}
     * @throws NullPointerException if color is {@code null}
     */
    public static ColorChooserAction colorChanged(Color newColor) {
        return new ColorChangedAction(newColor);
    }
}
