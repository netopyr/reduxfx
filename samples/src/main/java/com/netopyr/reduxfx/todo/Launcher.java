package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.ReduxFX;
import com.netopyr.reduxfx.todo.reducers.Todos;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.Filter;
import com.netopyr.reduxfx.todo.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import javaslang.collection.Array;

public class Launcher extends Application {

    public void start(Stage primaryStage) throws Exception {

        final AppModel initialState = new AppModel("", Array.empty(), Filter.ALL);
        final Todos reducer = new Todos();
        final MainView mainView = new MainView();

        ReduxFX.start(initialState, reducer, mainView, primaryStage);

        primaryStage.setTitle("TodoMVCFX - ReduxFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
