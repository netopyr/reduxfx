package com.netopyr.reduxfx.sample.externalstore;

import com.glung.redux.Store;
import com.netopyr.reduxfx.sample.externalstore.actions.Actions;
import com.netopyr.reduxfx.sample.externalstore.reducer.Reducer;
import com.netopyr.reduxfx.sample.externalstore.state.AppModel;
import com.netopyr.reduxfx.sample.externalstore.view.MainView;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import javafx.application.Application;
import javafx.stage.Stage;

public class ExternalStore extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Store<AppModel> store = (Store<AppModel>) Store.createStore(Reducer::reduce, AppModel.create(), null);

        final PublishProcessor<Object> actionStream = PublishProcessor.create();
        actionStream.subscribe(store::dispatch);

        final Flowable<AppModel> stateStream = Flowable.create(
                emitter -> store.subscribe(() -> emitter.onNext(store.getState())),
                BackpressureStrategy.BUFFER
        );

        final ReduxFXView<AppModel> view = ReduxFXView.create(
                MainView::view,
                primaryStage
        );
        view.connect(actionStream, stateStream);

        store.dispatch(Actions.initAction());

        primaryStage.setTitle("Example with Redux Java Store");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
