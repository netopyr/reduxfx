package com.netopyr.reduxfx.examples.screenswitch.actions;

import com.netopyr.reduxfx.examples.screenswitch.state.Screen;
import com.netopyr.reduxfx.examples.screenswitch.updater.Updater;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link Updater}, which performs the actual change.
 */
public final class Actions {

    private Actions() {
    }


    /**
     * This method generates an {@link SwitchScreenAction}.
     * <p>
     * This action is passed to the {@link Updater} when the application should show another screen.
     *
     * @param screen the new {@link Screen} to switch to
     * @return the {@code AddTodoAction}
     * @throws NullPointerException if {@code screen} is {@code null}
     */
    public static SwitchScreenAction switchScreen(Screen screen) {
        return new SwitchScreenAction(screen);
    }
}
