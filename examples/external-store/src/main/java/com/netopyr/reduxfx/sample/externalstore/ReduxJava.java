package com.netopyr.reduxfx.sample.externalstore;

import com.glung.redux.Store;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import javafx.application.Application;
import javafx.stage.Stage;

public class ReduxJava extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Store<Integer> store = (Store<Integer>) Store.createStore(Reducer::reduce, 0, null);

        final PublishProcessor<Object> actionStream = PublishProcessor.create();
        actionStream.subscribe(store::dispatch);

        final Flowable<Integer> stateStream = Flowable.create(
                emitter -> store.subscribe(() -> emitter.onNext(store.getState())),
                BackpressureStrategy.BUFFER
        );

        final ReduxFXView<Integer> view = ReduxFXView.create(
                MainView::view,
                primaryStage
        );
        view.connect(actionStream, stateStream);

        store.dispatch(Action.INIT);

        primaryStage.setTitle("Example with Redux Java Store");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
