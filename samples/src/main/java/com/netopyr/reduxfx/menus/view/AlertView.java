package com.netopyr.reduxfx.menus.view;

import com.netopyr.reduxfx.menus.actions.Actions;
import com.netopyr.reduxfx.menus.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.control.Alert;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Alert;

public class AlertView {

    private AlertView() {
    }

    public static VNode view(AppModel state) {
        return Alert()
                .alertType(Alert.AlertType.INFORMATION)
                .modal(state.getAlertModality())
                .headerText("This is the header")
                .contentText("This is the content")
                .showing(state.getAlertVisible(), (oldValue, newValue) -> newValue? Actions.noOp() : Actions.alertWasClosed());
    }

}
