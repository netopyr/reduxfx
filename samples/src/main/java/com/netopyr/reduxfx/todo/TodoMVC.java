package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.SimpleReduxFX;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.Filter;
import com.netopyr.reduxfx.todo.updater.Updater;
import com.netopyr.reduxfx.todo.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;
import javaslang.collection.Array;

public class TodoMVC extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        final AppModel initialState = new AppModel("", Array.empty(), Filter.ALL);

        SimpleReduxFX.start(initialState, Updater::update, MainView::view, primaryStage);

        primaryStage.setTitle("TodoMVCFX - ReduxFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
