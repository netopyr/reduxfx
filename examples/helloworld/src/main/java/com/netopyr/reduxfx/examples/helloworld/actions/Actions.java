package com.netopyr.reduxfx.examples.helloworld.actions;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 */
public class Actions {

    private static final IncCounterAction INC_COUNTER_ACTION = new IncCounterAction();

    private Actions() {}


    public static IncCounterAction incCounterAction() {
        return INC_COUNTER_ACTION;
    }

}
