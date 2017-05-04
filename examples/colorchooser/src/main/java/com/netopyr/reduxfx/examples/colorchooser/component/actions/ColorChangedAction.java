package com.netopyr.reduxfx.examples.colorchooser.component.actions;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A {@code ColorChangedAction} is passed to the  {@link ColorChooserUpdater} when the value of the color
 * property of this component was changed from outside.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link ColorChooserUpdater}, which performs the actual change.
 */
public final class ColorChangedAction {

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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("newColor", newColor)
                .toString();
    }
}
