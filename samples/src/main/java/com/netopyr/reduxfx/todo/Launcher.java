package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.ReduxFX;
import com.netopyr.reduxfx.todo.reducers.ToDos;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    public void start(Stage primaryStage) throws Exception {
        final StackPane root = new StackPane();
        root.setPrefWidth(800);
        root.setPrefHeight(600);
        root.setMinWidth(Region.USE_PREF_SIZE);
        root.setMinHeight(Region.USE_PREF_SIZE);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);

        primaryStage.setTitle("ReactFX on JavaFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        final ToDoEntry[] dummyEntries = new ToDoEntry[]{
                new ToDoEntry(0, "Buy milk", false),
                new ToDoEntry(1, "Buy bread", true),
                new ToDoEntry(2, "Buy juice", false)
        };

        final AppModel initialState = new AppModel("Hello World", dummyEntries);
        final ToDos reducer = new ToDos();
        final ToDoView toDoView = new ToDoView();

        ReduxFX.start(initialState, reducer, toDoView, root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
