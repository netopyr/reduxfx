package com.netopyr.reduxfx.colorchooser.app.actions;

import javafx.scene.paint.Color;

/**
 * The class {@code Actions} contains factory-methods for all {@link Actions}s that are available in this application.
 */
public final class Actions {

    private Actions() {}

    /**
     * This method generates an {@link UpdateColorAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.colorchooser.app.updater.Updater} when the
     * color selected in the {@link com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent} has changed.
     *
     * @param color the new color
     * @return the {@code UpdateColorAction}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public static Action updateColor(Color color) {
        return new UpdateColorAction(color);
    }
}
