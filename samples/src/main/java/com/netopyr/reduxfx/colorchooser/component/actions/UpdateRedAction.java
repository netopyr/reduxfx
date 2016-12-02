package com.netopyr.reduxfx.colorchooser.component.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An {@code UpdateRedAction} is passed to the
 * {@link com.netopyr.reduxfx.colorchooser.component.updater.ColorChooserUpdater} when the slider for red
 * has changed.
 */
public final class UpdateRedAction implements ColorChooserAction {

    private final int value;

    UpdateRedAction(int value) {
        this.value = value;
    }

    /**
     * The getter of the value for red
     * @return the new value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
