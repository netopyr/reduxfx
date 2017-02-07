package com.netopyr.reduxfx.screenswitch;

import com.netopyr.reduxfx.SimpleReduxFX;
import com.netopyr.reduxfx.screenswitch.state.AppModel;
import com.netopyr.reduxfx.screenswitch.updater.Updater;
import com.netopyr.reduxfx.screenswitch.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ScreenSwitch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        SimpleReduxFX.start(initialState, Updater::update, ViewManager::view, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
