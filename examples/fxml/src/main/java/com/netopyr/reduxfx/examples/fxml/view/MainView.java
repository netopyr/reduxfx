package com.netopyr.reduxfx.examples.fxml.view;

import com.netopyr.reduxfx.examples.fxml.actions.Actions;
import com.netopyr.reduxfx.examples.fxml.state.AppState;
import com.netopyr.reduxfx.fxml.Dispatcher;
import com.netopyr.reduxfx.fxml.Selector;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * The class {@code MainView} connects the view ,that is defined in an FXML-file, with the
 * {@link com.netopyr.reduxfx.examples.fxml.updater.Updater}.
 * <p>
 * {@code MainView} is a standard controller that uses bindings to push state-changes to the view.
 * Actions are dispatched by calling the method {@link Dispatcher#dispatch(Object)}.
 */
public class MainView {

    @FXML
    private Label value;

    private final Dispatcher dispatcher;

    private final Selector<AppState> selector;

	public MainView(Dispatcher dispatcher, Selector<AppState> selector) {
		this.dispatcher = dispatcher;
		this.selector = selector;
	}


	/**
     * The method {@code initialize} is used to define the bindings that map the application state
     * to the view.
     */
    public void initialize() {
        value.textProperty().bind(
                Bindings.format(
                        "You clicked the button %d times",
                        selector.select(AppState::getCounter)
                )
        );
    }

    /**
     * The method {@code increase()} is called when the user clicks the increase-button. It dispatches
     * an {@link com.netopyr.reduxfx.examples.fxml.actions.IncCounterAction}, which signals the
     * {@link com.netopyr.reduxfx.examples.fxml.updater.Updater} to increase the value by one.
     */
    public void increase() {
        dispatcher.dispatch(Actions.incCounterAction());
    }
}