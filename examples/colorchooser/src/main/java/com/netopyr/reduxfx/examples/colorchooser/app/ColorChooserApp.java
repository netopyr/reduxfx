package com.netopyr.reduxfx.examples.colorchooser.app;

import com.netopyr.reduxfx.examples.colorchooser.app.state.AppState;
import com.netopyr.reduxfx.examples.colorchooser.app.updater.Updater;
import com.netopyr.reduxfx.examples.colorchooser.app.view.MainView;
import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class ColorChooserApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setup the initial state
        final AppState initialState = AppState.create().withColor(Color.VIOLET);

        // Setup the ReduxFX-store passing the initialState and the update-function
        final ReduxFXStore<AppState> store = new ReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());

        // Setup the ReduxFX-view passing the store, the view-function and the primary stage that should hold the calculated view
        ReduxFXView.createStage(store, MainView::view, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
