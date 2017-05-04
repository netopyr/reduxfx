package com.netopyr.reduxfx.examples.helloworld;

import com.netopyr.reduxfx.examples.helloworld.view.MainView;
import com.netopyr.reduxfx.examples.helloworld.state.AppModel;
import com.netopyr.reduxfx.examples.helloworld.updater.Updater;
import com.netopyr.reduxfx.store.SimpleReduxFXStore;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class HelloWorld extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        // Setup the ReduxFX-store passing the initialState and the update-function
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update);

        // Setup the ReduxFX-view passing the view-function and the primary stage that should hold the calculated view
        final ReduxFXView<AppModel> view = ReduxFXView.create(MainView::view, primaryStage);

        // Connect store and view
        view.connect(store.createActionSubscriber(), store.getStatePublisher());

        // Show the application
        primaryStage.setTitle("HelloWorld - ReduxFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
