package com.netopyr.reduxfx.colorchooser.component.actions;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@code ColorChangedAction} is passed to the
 * {@link com.netopyr.reduxfx.colorchooser.component.updater.ColorChooserUpdater} when the value of the color
 * property of this component was changed from outside.
 */
public final class ColorChangedAction implements ColorChooserAction {

    private final Color newColor;

    ColorChangedAction(Color newColor) {
        this.newColor = newColor;
    }

    /**
     * This is the getter of the new color
     *
     * @return the new text
     */
    public Color getNewColor() {
        return newColor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("newColor", newColor)
                .toString();
    }
}
