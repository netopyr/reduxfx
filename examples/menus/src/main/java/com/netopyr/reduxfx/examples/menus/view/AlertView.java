package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.actions.Actions;
import com.netopyr.reduxfx.examples.menus.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.control.Alert;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Alert;

/**
 * The class {@code AlertView} is responsible for mapping the current state of the application, an instance of
 * {@link AppState}, to the VirtualScenegraph of a single {@code Alert}.
 *
 * For more information about the view-classes, please take a look at {@link ViewManager}.
 */
class AlertView {

    private AlertView() {
    }

    /**
     * The method {@code view} calculates a new {@code Alert} for the given state.
     *
     * @param state the current state of the application
     * @return the root {@link VNode} of the created {@code Alert}
     * @throws NullPointerException if {@code state} is {@code null}
     */
    static VNode view(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return Alert()
                .alertType(Alert.AlertType.INFORMATION)
                // The modality of the Alert is defined in the application state
                .modal(state.getAlertModality())
                .headerText("This is the header")
                .contentText("This is the content")
                // When the user closes the alert, we generate an AlertWasClosed-Action to update the application state
                .showing(state.getAlertVisible(), (oldValue, newValue) -> newValue ? Actions.noOp() : Actions.alertWasClosed());
    }

}
