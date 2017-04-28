package com.netopyr.reduxfx.examples.colorchooser.app.actions;

import com.netopyr.reduxfx.examples.colorchooser.app.updater.Updater;
import com.netopyr.reduxfx.examples.colorchooser.component.ColorChooserComponent;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An {@code UpdateColorAction} is passed to the {@link Updater} when the
 * color selected in the {@link ColorChooserComponent} has changed.
 */
public final class UpdateColorAction {

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
