package com.netopyr.reduxfx.vscenegraph;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Stages {

    private final ObservableList<Stage> children;

    public Stages(Stage primaryStage) {
        this.children = FXCollections.observableArrayList();
        this.children.addListener((ListChangeListener<Stage>) c -> {
            while (c.next()) {
                for (final Stage stage : c.getRemoved()) {
                    stage.getProperties().remove("stages");
                }
                for (final Stage stage : c.getAddedSubList()) {
                    stage.getProperties().put("stages", Stages.this);
                }
            }
        });
        this.children.add(primaryStage);
    }

    public ObservableList<Stage> getChildren() {
        return children;
    }
}
