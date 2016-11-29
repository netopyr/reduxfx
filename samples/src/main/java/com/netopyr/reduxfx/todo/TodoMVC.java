package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.SimpleReduxFX;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.updater.Updater;
import com.netopyr.reduxfx.todo.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class TodoMVC extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = new AppModel();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        SimpleReduxFX.start(initialState, Updater::update, MainView::view, primaryStage);

        primaryStage.setTitle("TodoMVCFX - ReduxFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
