package com.netopyr.reduxfx.examples.menus.actions;

import com.netopyr.reduxfx.examples.menus.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An {@code NoOpAction} is passed to the {@link Updater} when the interface requires an action, but no change is needed.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Updater}, which performs the actual change.
 */
public class NoOpAction {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
