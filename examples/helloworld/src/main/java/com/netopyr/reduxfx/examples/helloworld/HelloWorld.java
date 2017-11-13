package com.netopyr.reduxfx.examples.helloworld;

import com.netopyr.reduxfx.examples.helloworld.updater.Updater;
import com.netopyr.reduxfx.examples.helloworld.view.MainView;
import com.netopyr.reduxfx.store.ReduxFXStore;
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
        final Integer initialCounter = 0;

        // Setup the ReduxFX-store passing the initialState and the update-function
        final ReduxFXStore<Integer> store = new ReduxFXStore<>(initialCounter, Updater::update);

        // Setup the ReduxFX-view passing the store, the view-function and the primary stage that should hold the calculated view
        ReduxFXView.createStage(store, MainView::view, primaryStage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
