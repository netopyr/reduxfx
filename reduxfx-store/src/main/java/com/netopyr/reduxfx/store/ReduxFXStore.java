package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.driver.action.ActionDriver;
import com.netopyr.reduxfx.driver.http.HttpDriver;
import com.netopyr.reduxfx.driver.properties.PropertiesDriver;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.BiFunction;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ReduxFXStore<STATE> {

    private final PublishProcessor<Object> actionProcessor = PublishProcessor.create();
    private final Flowable<STATE> statePublisher;
    private final Flowable<Command> commandPublisher;


    public ReduxFXStore(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater) {
        final FlowableProcessor<Update<STATE>> updateProcessor = BehaviorProcessor.create();

        statePublisher = updateProcessor.map(Update::getState);

        statePublisher.zipWith(actionProcessor, updater::apply)
                .startWith(Update.of(initialState))
                .subscribe(updateProcessor);

        commandPublisher = updateProcessor
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands);

        registerDefaultDrivers();
    }


    public Subscriber<Object> createActionSubscriber() {
        final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
        actionSubscriber.subscribe(actionProcessor::offer);
        return actionSubscriber;
    }

    public Publisher<STATE> getStatePublisher() {
        return statePublisher;
    }

    public final void register(Driver driver) {
        commandPublisher.subscribe(driver.getCommandSubscriber());
        Flowable.fromPublisher(driver.getActionPublisher()).subscribe(actionProcessor::offer);
    }


    private void registerDefaultDrivers() {
        register(new PropertiesDriver());
        register(new HttpDriver());
        register(new ActionDriver());
    }

}