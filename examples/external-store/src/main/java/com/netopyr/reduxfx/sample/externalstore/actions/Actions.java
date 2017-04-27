package com.netopyr.reduxfx.sample.externalstore.actions;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 *
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link com.netopyr.reduxfx.sample.externalstore.reducer.Reducer},
 * which performs the actual change.
 */
public class Actions {

    // InitAction and IncCounterAction are stateless, therefore only a single instance can be reused
    private static final InitAction INIT_ACTION = new InitAction();
    private static final IncCounterAction INC_COUNTER_ACTION = new IncCounterAction();

    private Actions() {}

    /**
     * This method generates an {@link InitAction}.
     *
     * This action is passed to the {@link com.netopyr.reduxfx.sample.externalstore.reducer.Reducer} to
     * initialize the system.
     *
     * @return the {@code InitAction}
     */
    public static InitAction initAction() {
        return INIT_ACTION;
    }

    /**
     * This method generates an {@link IncCounterAction}.
     *
     * This action is passed to the {@link com.netopyr.reduxfx.sample.externalstore.reducer.Reducer}
     * when the button was pressed.
     *
     * @return the {@code IncCounterAction}
     */
    public static IncCounterAction incCounterAction() {
        return INC_COUNTER_ACTION;
    }

}
