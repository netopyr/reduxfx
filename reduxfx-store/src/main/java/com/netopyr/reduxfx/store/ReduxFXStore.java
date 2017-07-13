package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.driver.action.ActionDriver;
import com.netopyr.reduxfx.driver.http.HttpDriver;
import com.netopyr.reduxfx.driver.properties.PropertiesDriver;
import com.netopyr.reduxfx.middleware.Middleware;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.BiFunction;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ReduxFXStore<S> {

    private final Flowable<S> statePublisher;
    private final Flowable<Command> commandPublisher;
    private FlowableEmitter<Publisher<?>> emitter;


    @SafeVarargs
    public ReduxFXStore(S initialState, BiFunction<S, Object, Update<S>> updater, Middleware<S>... middlewares) {
        final BiFunction<S, Object, Update<S>> chainedUpdater = applyMiddlewares(updater, middlewares);

        final Publisher<Object> actionPublisher =
                Flowable.mergeDelayError(
                        Flowable.create(emitter -> this.emitter = emitter, BackpressureStrategy.BUFFER)
                );

        final FlowableProcessor<Update<S>> updateProcessor = BehaviorProcessor.create();

        statePublisher = updateProcessor.map(Update::getState)
                .startWith(initialState);

        statePublisher.zipWith(actionPublisher, chainedUpdater::apply)
                .subscribe(updateProcessor);

        commandPublisher = updateProcessor
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands);

        registerDefaultDrivers();
    }

    private BiFunction<S, Object, Update<S>> applyMiddlewares(BiFunction<S, Object, Update<S>> updater, Middleware<S>[] middlewares) {
        BiFunction<S, Object, Update<S>> result = updater;
        for (final Middleware<S> middleware : middlewares) {
            result = middleware.apply(result);
        }
        return result;
    }


    public Subscriber<Object> createActionSubscriber() {
        final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
        emitter.onNext(actionSubscriber);
        return actionSubscriber;
    }

    public Publisher<S> getStatePublisher() {
        return statePublisher;
    }

    public final void register(Driver driver) {
        commandPublisher.subscribe(driver.getCommandSubscriber());
        emitter.onNext(driver.getActionPublisher());
    }


    private void registerDefaultDrivers() {
        register(new PropertiesDriver());
        register(new HttpDriver());
        register(new ActionDriver());
    }

}