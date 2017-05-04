package com.netopyr.reduxfx.examples.fxml.actions;

import com.netopyr.reduxfx.examples.fxml.updater.Updater;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link Updater}, which performs the actual change.
 */
public class Actions {

    // IncCounterAction is stateless, therefore only a single instance can be reused
    private static final IncCounterAction INC_COUNTER_ACTION = new IncCounterAction();

    private Actions() {
    }

    /**
     * This method returns a {@link IncCounterAction}.
     * <p>
     * This action is passed to the {@link Updater} when the user clicks the button
     *
     * @return the {@code IncCounterAction}
     */
    public static IncCounterAction incCounterAction() {
        return INC_COUNTER_ACTION;
    }

}
