package com.netopyr.reduxfx.sample.externalstore.actions;

/**
 * The class {@code Actions} contains factory-methods for all {@link Actions}s that are available in this application.
 */
public class Actions {

    private static final InitAction INIT_ACTION = new InitAction();
    private static final IncCounterAction INC_COUNTER_ACTION = new IncCounterAction();

    private Actions() {}

    public static InitAction initAction() {
        return INIT_ACTION;
    }

    public static IncCounterAction incCounterAction() {
        return INC_COUNTER_ACTION;
    }

}
