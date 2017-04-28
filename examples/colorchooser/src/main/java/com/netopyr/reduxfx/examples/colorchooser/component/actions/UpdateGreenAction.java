package com.netopyr.reduxfx.examples.colorchooser.component.actions;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An {@code UpdateGreenAction} is passed to the
 * {@link ColorChooserUpdater} when the slider for green
 * has changed.
 */
public final class UpdateGreenAction implements ColorChooserAction {

    private final int value;

    UpdateGreenAction(int value) {
        this.value = value;
    }

    /**
     * The getter of the new value for green
     * @return the new value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", value)
                .toString();
    }
}
