package com.netopyr.reduxfx.colorchooser.app.actions;

import javafx.scene.paint.Color;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 *
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link com.netopyr.reduxfx.colorchooser.app.updater.Updater},
 * which performs the actual change.
 */
public final class Actions {

    private Actions() {}

    /**
     * This method generates an {@link UpdateColorAction}.
     *
     * This action is passed to the {@link com.netopyr.reduxfx.colorchooser.app.updater.Updater} when the
     * color selected in the {@link com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent} has changed.
     *
     * @param color the new color
     * @return the {@code UpdateColorAction}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public static UpdateColorAction updateColor(Color color) {
        return new UpdateColorAction(color);
    }
}
