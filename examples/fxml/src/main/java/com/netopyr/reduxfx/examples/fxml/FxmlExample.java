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

/**
 * This is the launcher of the application.
 */
public class FxmlExample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Setup the ReduxFX-store passing the initialState and the update-function
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());

        // To setup ReduxJavaFX, we have to connect it with the store
        ReduxJavaFX.connect(store.createActionSubscriber(), store.getStatePublisher());

        // ViewLoader.load() loads the FXML-file and sets up the controller
        final Parent root = ViewLoader.load(MainView.class);

        // Setup the stage and show it
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("FXML Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
