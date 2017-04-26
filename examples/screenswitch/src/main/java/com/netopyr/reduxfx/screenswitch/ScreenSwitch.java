package com.netopyr.reduxfx.screenswitch;

import com.netopyr.reduxfx.screenswitch.state.AppModel;
import com.netopyr.reduxfx.screenswitch.updater.Updater;
import com.netopyr.reduxfx.screenswitch.view.ViewManager;
import com.netopyr.reduxfx.store.SimpleReduxFXStore;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import javafx.application.Application;
import javafx.stage.Stage;

public class ScreenSwitch extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update);
        final ReduxFXView<AppModel> view = ReduxFXView.createStages(ViewManager::view, primaryStage);
        view.connect(store.createActionSubscriber(), store.getStatePublisher());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
