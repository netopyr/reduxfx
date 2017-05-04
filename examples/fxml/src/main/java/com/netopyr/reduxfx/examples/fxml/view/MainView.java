package com.netopyr.reduxfx.examples.fxml.view;

import com.netopyr.reduxfx.examples.fxml.actions.Actions;
import com.netopyr.reduxfx.examples.fxml.reduxjavafx.View;
import com.netopyr.reduxfx.examples.fxml.state.AppModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainView implements View {
    @FXML
    private Label value;

    public void initialize() {
        value.textProperty().bind(
                Bindings.format(
                        "You clicked the button %d times",
                        select(AppModel::getCounter)
                )
        );
    }

    public void increase() {
        dispatch(Actions.incCounterAction());
    }
}