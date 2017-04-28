package com.netopyr.reduxfx.examples.fxml.actions;

/**
 * The class {@code Actions} contains factory-methods for all {@code Actions} that are available in this application.
 */
public class Actions {

    private static final IncCounterAction INC_COUNTER_ACTION = new IncCounterAction();

    private Actions() {}

    public static IncCounterAction incCounterAction() {
        return INC_COUNTER_ACTION;
    }

}
