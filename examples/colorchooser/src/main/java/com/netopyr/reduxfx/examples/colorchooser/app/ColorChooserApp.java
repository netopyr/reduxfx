package com.netopyr.reduxfx.examples.colorchooser.app;

import com.netopyr.reduxfx.examples.colorchooser.app.state.AppModel;
import com.netopyr.reduxfx.examples.colorchooser.app.updater.Updater;
import com.netopyr.reduxfx.examples.colorchooser.app.view.MainView;
import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.store.SimpleReduxFXStore;
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
        final AppModel initialState = AppModel.create().withColor(Color.VIOLET);

        // Setup the ReduxFX-store passing the initialState and the update-function
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());

        // Setup the ReduxFX-view passing the view-function and the primary stage that should hold the calculated view
        final ReduxFXView<AppModel> view = ReduxFXView.createStage(MainView::view, primaryStage);

        // Connect store and view
        view.connect(store.getStatePublisher(), store.createActionSubscriber());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
