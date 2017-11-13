package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.middleware.Middleware;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.BiFunction;

public class ReduxFXStore<S> implements Publisher<S> {

    private final Flowable<S> statePublisher;
    private final Flowable<Command> commandPublisher;
    private FlowableEmitter<Object> actionEmitter;


    @SafeVarargs
    public ReduxFXStore(S initialState, BiFunction<S, Object, Update<S>> updater, Middleware<S>... middlewares) {
        final BiFunction<S, Object, Update<S>> chainedUpdater = applyMiddlewares(updater, middlewares);

        final Publisher<Object> actionPublisher =
                Flowable.create(actionEmitter -> this.actionEmitter = actionEmitter, BackpressureStrategy.BUFFER);

        final FlowableProcessor<Update<S>> updateProcessor = BehaviorProcessor.create();

        statePublisher = updateProcessor.map(Update::getState)
                .startWith(initialState);

        statePublisher.zipWith(actionPublisher, chainedUpdater::apply)
                .subscribe(updateProcessor);

        commandPublisher = updateProcessor
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands);
    }

    private BiFunction<S, Object, Update<S>> applyMiddlewares(BiFunction<S, Object, Update<S>> updater, Middleware<S>[] middlewares) {
        BiFunction<S, Object, Update<S>> result = updater;
        for (final Middleware<S> middleware : middlewares) {
            result = middleware.apply(result);
        }
        return result;
    }


    public void dispatch(Object action) {
        actionEmitter.onNext(action);
    }

    @Override
    public void subscribe(Subscriber<? super S> subscriber) {
        statePublisher.subscribe(subscriber);
    }

    public final void register(Driver driver) {
        Flowable.fromPublisher(driver).subscribe(this::dispatch);
        commandPublisher.subscribe(driver::dispatch);
    }
}