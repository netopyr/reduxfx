package com.netopyr.reduxfx.examples.menus.actions;

/**
 * The class {@code Actions} contains factory-methods for all {@link Action}s that are available in this application.
 */
public final class Actions {

    private Actions() {}


    public static Action openAlert() {
        return new OpenAlertAction();
    }

    public static Action openModalAlert() {
        return new OpenModalAlertAction();
    }

    public static Action alertWasClosed() {
        return new AlertWasClosedAction();
    }

    public static Action noOp() {
        return new NoOpAction();
    }
}
