package com.netopyr.reduxfx.examples.externalstore;

import com.glung.redux.Store;
import com.netopyr.reduxfx.examples.externalstore.reducer.Reducer;
import com.netopyr.reduxfx.examples.externalstore.view.MainView;
import com.netopyr.reduxfx.examples.externalstore.actions.Actions;
import com.netopyr.reduxfx.examples.externalstore.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the launcher of the application.
 */
public class ExternalStore extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a store with redux-java
        final Store<AppModel> store = (Store<AppModel>) Store.createStore(Reducer::reduce, AppModel.create(), null);

        // ReduxFX uses reactive streams to handle its inputs and outputs
        // To handle the input, we create a Publisher that contains all elements emitted by the store
        final Flowable<AppModel> statePublisher = Flowable.create(
                emitter -> store.subscribe(() -> emitter.onNext(store.getState())),
                BackpressureStrategy.BUFFER
        );

        // To handle the output of the ReduxFView, we create a Subscriber that calls store.dispatch() for each action
        final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
        actionSubscriber.subscribe(store::dispatch);

        // Setup the ReduxFX-view passing the view-function and the primary stage that should hold the calculated view
        final ReduxFXView<AppModel> view = ReduxFXView.createStage(MainView::view, primaryStage);

        // Connect store and view
        view.connect(statePublisher, actionSubscriber);

        // To initialize redux-java, we have to send an init-action
        store.dispatch(Actions.initAction());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
