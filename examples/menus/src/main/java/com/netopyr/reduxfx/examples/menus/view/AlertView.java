package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.actions.Actions;
import com.netopyr.reduxfx.examples.menus.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.control.Alert;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Alert;

class AlertView {

    private AlertView() {
    }

    static VNode view(AppModel state) {
        return Alert()
                .alertType(Alert.AlertType.INFORMATION)
                .modal(state.getAlertModality())
                .headerText("This is the header")
                .contentText("This is the content")
                .showing(state.getAlertVisible(), (oldValue, newValue) -> newValue ? Actions.noOp() : Actions.alertWasClosed());
    }

}
