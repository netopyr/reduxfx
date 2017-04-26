package com.netopyr.reduxfx.samples.fxml.reduxjavafx;

import io.reactivex.processors.PublishProcessor;
import javafx.application.Platform;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Consumer;

public class ReduxJavaFX<STATE> {

    @SuppressWarnings("unchecked")
    public static <STATE> ReduxJavaFX<STATE> getSingleton() {
        if (SINGLETON == null) {
            throw new IllegalStateException("ReduxJavaFX has not been connected to a ReduxStore yet.");
        }
        return SINGLETON;
    }


    private static ReduxJavaFX SINGLETON;
    private final PublishProcessor<Object> actionProcessor = PublishProcessor.create();
    private final Publisher<STATE> statePublisher;


    private ReduxJavaFX(Subscriber<Object> actionStream, Publisher<STATE> statePublisher) {
        this.statePublisher = statePublisher;

        actionProcessor.subscribe(actionStream);
    }

    protected void dispatch(Object action) {
        actionProcessor.offer(action);
    }

    protected void addSubscriber(Consumer<STATE> subscriber) {
        statePublisher.subscribe(new Subscriber<STATE>() {

            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(STATE state) {
                if (Platform.isFxApplicationThread()) {
                    subscriber.accept(state);
                } else {
                    Platform.runLater(() -> subscriber.accept(state));
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public static <STATE> void connect(Subscriber<Object> actionStream, Publisher<STATE> statePublisher) {
        SINGLETON = new ReduxJavaFX<>(actionStream, statePublisher);
    }

}