package com.netopyr.reduxfx.samples.fxml;

import com.netopyr.reduxfx.samples.fxml.reduxjavafx.ReduxJavaFX;
import com.netopyr.reduxfx.samples.fxml.reduxjavafx.ViewLoader;
import com.netopyr.reduxfx.samples.fxml.state.AppModel;
import com.netopyr.reduxfx.samples.fxml.updater.Updater;
import com.netopyr.reduxfx.samples.fxml.view.MainView;
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
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update);
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
