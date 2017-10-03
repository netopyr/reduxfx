package com.netopyr.reduxfx.examples.todo;

import com.netopyr.reduxfx.examples.todo.actions.Actions;
import com.netopyr.reduxfx.examples.todo.state.AppState;
import com.netopyr.reduxfx.examples.todo.updater.Updater;
import com.netopyr.reduxfx.examples.todo.view.MainView;
import com.netopyr.reduxfx.middleware.LoggingMiddleware;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.store.SimpleReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import eu.lestard.redux_javafx_devtool.ReduxFXDevTool;
import eu.lestard.redux_javafx_devtool.ReduxFXDevToolConnector;
import io.reactivex.Flowable;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class TodoMVC extends Application {

    private static final boolean DEBUG = true;

    @Override
    public void start(Stage primaryStage) throws Exception {

        if(DEBUG) {
            // Create dev-tool instance
            final ReduxFXDevTool<AppState> devTool = ReduxFXDevTool.create();

            // create a connector for reduxfx.
            // The connector is used to glue together the dev-tool (which is independent from reduxfx)
            // with the reduxfx-specific setup-code.
            final ReduxFXDevToolConnector<AppState> reduxfxDevToolConnector = new ReduxFXDevToolConnector<>();
            // connect the dev-tool with the reduxfx-specific connector.
            devTool.connect(reduxfxDevToolConnector);


            // Setup the initial state
            final AppState initialState = AppState.create();

            // Setup the ReduxFX-store passing the initialState and the update-function.
            // As third parameter we pass the dev-tool-connector which also is a reduxfx middleware.
            final ReduxFXStore<AppState> store = new ReduxFXStore<>(initialState,
                (appState, action) -> Update.of(Updater.update(appState, action)),
                reduxfxDevToolConnector
            );


            // Setup the ReduxFX-view passing the view-function and the primary stage that should hold the calculated view
            final ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);

            // Connect store and dev-tool with the view.
            // Instead of connecting the statePublisher from the store directly, we pass the statePublisher from the dev-tool.
            // This enables time-travel debugging. The dev-tool can now control the state that's presented by the view.
            view.connect(reduxfxDevToolConnector.getStatePublisher(), store.createActionSubscriber());

            // Open the dev-tool UI. The primary stage is used as parent by the dev-tool.
            devTool.openDevToolWindow(primaryStage);

            // To initialize the dev-tool we need to publish an initial action.
            Flowable.just(Actions.init()).subscribe(store.createActionSubscriber());
        } else {
            // Setup the initial state
            final AppState initialState = AppState.create();

            // Setup the ReduxFX-store passing the initialState and the update-function
            final SimpleReduxFXStore<AppState> store = new SimpleReduxFXStore<>(initialState, Updater::update, new LoggingMiddleware<>());

            // Setup the ReduxFX-view passing the view-function and the primary stage that should hold the calculated view
            final ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);

            // Connect store and view
            view.connect(store.getStatePublisher(), store.createActionSubscriber());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
