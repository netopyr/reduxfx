package com.netopyr.reduxfx.examples.colorchooser.app.actions;

import com.netopyr.reduxfx.examples.colorchooser.app.updater.Updater;
import com.netopyr.reduxfx.examples.colorchooser.component.ColorChooserComponent;
import javafx.scene.paint.Color;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link Updater}, which performs the actual change.
 */
public class Actions {

    private Actions() {
    }

    /**
     * This method generates an {@link UpdateColorAction}.
     * <p>
     * This action is passed to the {@link Updater} when the  color selected in the {@link ColorChooserComponent} has
     * changed.
     *
     * @param color the new color
     * @return the {@code UpdateColorAction}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public static UpdateColorAction updateColor(Color color) {
        return new UpdateColorAction(color);
    }
}
