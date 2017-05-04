package com.netopyr.reduxfx.examples.externalstore.actions;

import com.netopyr.reduxfx.examples.externalstore.reducer.Reducer;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link Reducer}, which performs the actual change.
 */
public class Actions {

    // InitAction and IncCounterAction are stateless, therefore only a single instance can be reused
    private static final InitAction INIT_ACTION = new InitAction();
    private static final IncCounterAction INC_COUNTER_ACTION = new IncCounterAction();

    private Actions() {
    }

    /**
     * This method generates an {@link InitAction}.
     * <p>
     * This action is passed to the {@link Reducer} to initialize the system.
     *
     * @return the {@code InitAction}
     */
    public static InitAction initAction() {
        return INIT_ACTION;
    }

    /**
     * This method generates an {@link IncCounterAction}.
     * <p>
     * This action is passed to the {@link Reducer} when the button was pressed.
     *
     * @return the {@code IncCounterAction}
     */
    public static IncCounterAction incCounterAction() {
        return INC_COUNTER_ACTION;
    }

}
