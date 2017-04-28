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

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());
        final ReduxFXView<AppModel> view = ReduxFXView.create(MainView::view, primaryStage);
        view.connect(store.createActionSubscriber(), store.getStatePublisher());

        primaryStage.setTitle("ColorChooser Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
