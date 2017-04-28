package com.netopyr.reduxfx.examples.fxml.view;

import com.netopyr.reduxfx.examples.fxml.state.Selectors;
import com.netopyr.reduxfx.examples.fxml.actions.Actions;
import com.netopyr.reduxfx.examples.fxml.reduxjavafx.View;
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