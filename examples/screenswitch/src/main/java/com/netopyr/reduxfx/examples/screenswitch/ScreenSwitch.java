package com.netopyr.reduxfx.examples.screenswitch;

import com.netopyr.reduxfx.examples.screenswitch.state.AppState;
import com.netopyr.reduxfx.examples.screenswitch.updater.Updater;
import com.netopyr.reduxfx.examples.screenswitch.view.ViewManager;
import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class ScreenSwitch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppState initialState = AppState.create();

        // Setup the ReduxFX-store passing the initialState and the update-function
        final ReduxFXStore<AppState> store = new ReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());

        // Setup the ReduxFX-view passing the store, the view-function and the primary stage that should hold the calculated view
        ReduxFXView.createStages(store, ViewManager::view, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
