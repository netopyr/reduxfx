package com.netopyr.reduxfx.vscenegraph;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public enum VNodeType {
    ROOT(null),
    STACK_PANE(StackPane.class),
    V_BOX(VBox.class),
    H_BOX(HBox.class),
    LIST_VIEW(ListView.class),
    TEXT_FIELD(TextField.class),
    BUTTON(Button.class),
    TOGGLE_BUTTON(ToggleButton.class);

    private final Class<? extends Node> nodeClass;

    VNodeType(Class<? extends Node> nodeClass) {
        this.nodeClass = nodeClass;
    }

    public Class<? extends Node> getNodeClass() {
        return nodeClass;
    }
}
