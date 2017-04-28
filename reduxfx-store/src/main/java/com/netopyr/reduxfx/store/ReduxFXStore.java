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
public class ReduxFXStore<STATE> {

    private final Flowable<STATE> statePublisher;
    private final Flowable<Command> commandPublisher;
    private FlowableEmitter<Publisher<?>> emitter;


    @SafeVarargs
    public ReduxFXStore(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater, Middleware<STATE>... middlewares) {
        final BiFunction<STATE, Object, Update<STATE>> chainedUpdater = applyMiddlewares(updater, middlewares);

        final Publisher<Object> actionPublisher =
                Flowable.mergeDelayError(
                        Flowable.create(emitter -> this.emitter = emitter, BackpressureStrategy.BUFFER)
                );

        final FlowableProcessor<Update<STATE>> updateProcessor = BehaviorProcessor.create();

        statePublisher = updateProcessor.map(Update::getState)
                .startWith(initialState);

        statePublisher.zipWith(actionPublisher, chainedUpdater::apply)
                .subscribe(updateProcessor);

        commandPublisher = updateProcessor
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands);

        registerDefaultDrivers();
    }

    private BiFunction<STATE, Object, Update<STATE>> applyMiddlewares(BiFunction<STATE, Object, Update<STATE>> updater, Middleware<STATE>[] middlewares) {
        BiFunction<STATE, Object, Update<STATE>> result = updater;
        for (final Middleware<STATE> middleware : middlewares) {
            result = middleware.apply(result);
        }
        return result;
    }


    public Subscriber<Object> createActionSubscriber() {
        final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
        emitter.onNext(actionSubscriber);
        return actionSubscriber;
    }

    public Publisher<STATE> getStatePublisher() {
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