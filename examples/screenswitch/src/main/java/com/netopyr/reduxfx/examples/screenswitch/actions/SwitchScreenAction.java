package com.netopyr.reduxfx.examples.screenswitch.actions;

import com.netopyr.reduxfx.examples.screenswitch.state.Screen;
import com.netopyr.reduxfx.examples.screenswitch.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * A {@code SwitchScreenAction} is passed to the {@link Updater} when the application should show another screen.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Updater}, which performs the actual change.
 */
public final class SwitchScreenAction {

    private final Screen screen;

    SwitchScreenAction(Screen screen) {
        Objects.requireNonNull(screen, "The parameter 'screen' must not be null");
        this.screen = screen;
    }

    /**
     * This is the getter for the {@link Screen} that should be shown
     *
     * @return the {@code Screen}
     */
    public Screen getScreen() {
        return screen;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("screen", screen)
                .toString();
    }
}
