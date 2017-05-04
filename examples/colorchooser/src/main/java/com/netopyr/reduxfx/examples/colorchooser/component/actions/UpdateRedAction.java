package com.netopyr.reduxfx.examples.colorchooser.component.actions;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An {@code UpdateRedAction} is passed to the {@link ColorChooserUpdater} when the slider for red has changed.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link ColorChooserUpdater}, which performs the actual change.
 */
public final class UpdateRedAction {

    private final int value;

    UpdateRedAction(int value) {
        this.value = value;
    }

    /**
     * The getter of the value for red
     *
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
