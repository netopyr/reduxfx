package com.netopyr.reduxfx.samples.fxml.view;

import com.netopyr.reduxfx.samples.fxml.actions.Actions;
import com.netopyr.reduxfx.samples.fxml.reduxjavafx.View;
import com.netopyr.reduxfx.samples.fxml.state.Selectors;
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
                        select(Selectors.currentValue)
                )
        );
    }

    public void increase() {
        dispatch(Actions.incCounterAction());
    }
}