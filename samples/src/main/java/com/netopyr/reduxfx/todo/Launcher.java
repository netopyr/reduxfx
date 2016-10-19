package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.ReduxFX;
import com.netopyr.reduxfx.todo.reducers.ToDos;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.Filter;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.todo.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javaslang.collection.Array;

public class Launcher extends Application {

    public void start(Stage primaryStage) throws Exception {

        final StackPane root = new StackPane();
        root.setMinWidth(Region.USE_PREF_SIZE);
        root.setMinHeight(Region.USE_PREF_SIZE);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);

        final Array<ToDoEntry> entries = Array.of(
                new ToDoEntry(1, "Visit Java Basel", true, false, false),
                new ToDoEntry(2, "Visit Javaland", false, false, false)
        );

        final AppModel initialState = new AppModel("", entries, Filter.ALL);
        final ToDos reducer = new ToDos();
        final MainView mainView = new MainView();

        final ReduxFX reduxFX = new ReduxFX();
        reduxFX.start(initialState, reducer, mainView, root);

        primaryStage.setTitle("ToDoMVCFX - ReduxFX");
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
