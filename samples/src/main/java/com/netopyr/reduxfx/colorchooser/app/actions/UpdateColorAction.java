package com.netopyr.reduxfx.colorchooser.app.actions;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An {@code UpdateColorAction} is passed to the {@link com.netopyr.reduxfx.colorchooser.app.updater.Updater} when the
 * color selected in the {@link com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent} has changed.
 */
public final class UpdateColorAction implements Action {

    private final Color value;

    UpdateColorAction(Color color) {
        Objects.requireNonNull(color, "The parameter 'color' must not be null");
        this.value = color;
    }

    /**
     * This is the getter of the new color
     *
     * @return the new color
     */
    public Color getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", value)
                .toString();
    }
}
