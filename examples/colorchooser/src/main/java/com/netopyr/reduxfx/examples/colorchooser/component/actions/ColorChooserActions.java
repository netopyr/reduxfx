package com.netopyr.reduxfx.examples.colorchooser.component.actions;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import javafx.scene.paint.Color;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link ColorChooserUpdater}, which performs the actual change.
 */
public class ColorChooserActions {

    private ColorChooserActions() {
    }


    /**
     * This method generates an {@link UpdateRedAction}.
     * <p>
     * This action is passed to the {@link ColorChooserUpdater} when the slider for red has changed.
     *
     * @param value the new value for red
     * @return the {@code UpdateRedAction}
     */
    public static UpdateRedAction updateRed(int value) {
        return new UpdateRedAction(value);
    }


    /**
     * This method generates an {@link UpdateGreenAction}.
     * <p>
     * This action is passed to the {@link ColorChooserUpdater} when the slider for green has changed.
     *
     * @param value the new value for green
     * @return the {@code UpdateGreenAction}
     */
    public static UpdateGreenAction updateGreen(int value) {
        return new UpdateGreenAction(value);
    }


    /**
     * This method generates an {@link UpdateBlueAction}.
     * <p>
     * This action is passed to the {@link ColorChooserUpdater} when the slider for blue has changed.
     *
     * @param value the new value for blue
     * @return the {@code UpdateBlueAction}
     */
    public static UpdateBlueAction updateBlue(int value) {
        return new UpdateBlueAction(value);
    }


    /**
     * This method generates an {@link ColorChangedAction}.
     * <p>
     * This action is passed to the {@link ColorChooserUpdater} when the value of the color property of this component
     * was changed from outside.
     *
     * @param newColor the new color
     * @return the {@code ColorChangedAction}
     * @throws NullPointerException if color is {@code null}
     */
    public static ColorChangedAction colorChanged(Color newColor) {
        return new ColorChangedAction(newColor);
    }
}
