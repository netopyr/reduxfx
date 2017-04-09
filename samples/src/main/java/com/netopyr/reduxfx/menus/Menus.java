package com.netopyr.reduxfx.menus;

import com.netopyr.reduxfx.SimpleReduxFX;
import com.netopyr.reduxfx.menus.state.AppModel;
import com.netopyr.reduxfx.menus.updater.Updater;
import com.netopyr.reduxfx.menus.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Menus extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        SimpleReduxFX.startStages(initialState, Updater::update, ViewManager::view, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
