package com.netopyr.reduxfx.fxml;

/**
 * {@link Dispatcher} is a util to dispatch new Redux actions from FXML controllers.
 * <p>
 * An instance of this class should be injected into the Controller class so that the
 * controller can create and dispatch new actions based on user interactions.
 * <p>
 * To get an instance of the {@link Dispatcher} you will typically use a dependency injection library that can inject
 * an instance into your Controller class. The dependency injection library has to use the {@link ReduxFxml} instance
 * in this case. {@link ReduxFxml} implements this interface and connects it to the ReduxFX store.
 */
public interface Dispatcher {

    /**
     * Dispatch the given action to the ReduxFX Store.
     *
     * @param action the action
     */
    void dispatch(Object action);

}
