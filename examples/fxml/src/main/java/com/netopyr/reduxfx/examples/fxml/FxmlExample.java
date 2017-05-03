package com.netopyr.reduxfx.examples.fxml;

import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.examples.fxml.reduxjavafx.ReduxJavaFX;
import com.netopyr.reduxfx.examples.fxml.reduxjavafx.ViewLoader;
import com.netopyr.reduxfx.examples.fxml.state.AppModel;
import com.netopyr.reduxfx.examples.fxml.updater.Updater;
import com.netopyr.reduxfx.examples.fxml.view.MainView;
import com.netopyr.reduxfx.store.SimpleReduxFXStore;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxmlExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());
        ReduxJavaFX.connect(store.createActionSubscriber(), store.getStatePublisher());


        Parent root = ViewLoader.load(MainView.class);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("FXML Example");
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}