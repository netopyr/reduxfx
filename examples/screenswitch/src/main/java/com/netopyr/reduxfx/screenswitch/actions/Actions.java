package com.netopyr.reduxfx.screenswitch.actions;

import com.netopyr.reduxfx.screenswitch.state.Screen;

/**
 * The class {@code Actions} contains factory-methods for all {@link Action}s that are available in this application.
 */
public final class Actions {

    private Actions() {}



    /**
     * This method generates an {@link SwitchScreenAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.screenswitch.updater.Updater} when the
     * should show another screen.
     *
     * @param screen the new {@link Screen} to switch to
     * @return the {@code AddTodoAction}
     */
    public static Action switchScreen(Screen screen) {
        return new SwitchScreenAction(screen);
    }
}
