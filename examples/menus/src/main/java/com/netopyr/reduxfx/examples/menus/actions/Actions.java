package com.netopyr.reduxfx.examples.menus.actions;

import com.netopyr.reduxfx.examples.menus.updater.Updater;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link Updater}, which performs the actual change.
 */
public final class Actions {

    // All actions are stateless, therefore single instances can be reused
    private static final OpenAlertAction OPEN_ALERT_ACTION = new OpenAlertAction();
    private static final OpenModalAlertAction OPEN_MODAL_ALERT_ACTION = new OpenModalAlertAction();
    private static final AlertWasClosedAction ALERT_WAS_CLOSED_ACTION = new AlertWasClosedAction();
    private static final NoOpAction NO_OP_ACTION = new NoOpAction();

    private Actions() {
    }


    /**
     * This method returns a {@link OpenAlertAction}.
     * <p>
     * This action is passed to the {@link Updater} when the alert should be opened.
     *
     * @return the {@code OpenAlertAction}
     */
    public static OpenAlertAction openAlert() {
        return OPEN_ALERT_ACTION;
    }


    /**
     * This method returns a {@link OpenModalAlertAction}.
     * <p>
     * This action is passed to the {@link Updater} when the modal alert should be opened.
     *
     * @return the {@code OpenModalAlertAction}
     */
    public static OpenModalAlertAction openModalAlert() {
        return OPEN_MODAL_ALERT_ACTION;
    }


    /**
     * This method returns a {@link AlertWasClosedAction}.
     * <p>
     * This action is passed to the {@link Updater} when the alert was closed.
     *
     * @return the {@code AlertWasClosedAction}
     */
    public static AlertWasClosedAction alertWasClosed() {
        return ALERT_WAS_CLOSED_ACTION;
    }


    /**
     * This method returns a {@link NoOpAction}.
     * <p>
     * This action is passed to the {@link Updater} when an action is required, but actually nothing should change.
     *
     * @return the {@code NoOpAction}
     */
    public static NoOpAction noOp() {
        return NO_OP_ACTION;
    }
}
