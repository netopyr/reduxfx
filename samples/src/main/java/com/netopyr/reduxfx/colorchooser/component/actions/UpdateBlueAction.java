package com.netopyr.reduxfx.colorchooser.component.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An {@code UpdateBlueAction} is passed to the
 * {@link com.netopyr.reduxfx.colorchooser.component.updater.ColorChooserUpdater} when the slider for blue
 * has changed.
 */
public final class UpdateBlueAction implements ColorChooserAction {

    private final int value;

    UpdateBlueAction(int value) {
        this.value = value;
    }

    /**
     * The getter of the new value for blue
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
