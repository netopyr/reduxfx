package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.ReduxFX;
import com.netopyr.reduxfx.todo.reducers.Todos;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.Filter;
import com.netopyr.reduxfx.todo.state.TodoEntry;
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

        final Array<TodoEntry> entries = Array.of(
                new TodoEntry(1, "Visit Java Basel", true, false, false),
                new TodoEntry(2, "Visit Javaland", false, false, false)
        );

        final AppModel initialState = new AppModel("", entries, Filter.ALL);
        final Todos reducer = new Todos();
        final MainView mainView = new MainView();

        final ReduxFX reduxFX = new ReduxFX();
        reduxFX.start(initialState, reducer, mainView, root);

        primaryStage.setTitle("TodoMVCFX - ReduxFX");
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
