package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.store.SimpleReduxFXStore;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.updater.Updater;
import com.netopyr.reduxfx.todo.view.MainView;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class TodoMVC extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setup the initial state
        final AppModel initialState = AppModel.create();

        // Start the ReduxFX application by passing the initial state, the update-function, the view-function, and
        // the stage to use with the resulting SceneGraph.
        final SimpleReduxFXStore<AppModel> store = new SimpleReduxFXStore<>(initialState, Updater::update);
        final ReduxFXView<AppModel> view = ReduxFXView.create(MainView::view, primaryStage);
        view.connect(store.createActionSubscriber(), store.getStatePublisher());

        primaryStage.setTitle("TodoMVCFX - ReduxFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
