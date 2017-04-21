package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class ReduxFXStore<STATE> {

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        final Thread thread = new Thread(runnable, "ReduxFXLoop-" + THREAD_COUNTER.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    };

    private final PublishProcessor<Object> actionProcessor = PublishProcessor.create();
    private final Flowable<STATE> statePublisher;
    private final Flowable<Command> commandPublisher;
    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();

    private ExecutorService executor;


    public ReduxFXStore(STATE initialState, BiFunction<STATE, Object, Update<STATE>> updater) {
        final FlowableProcessor<Update<STATE>> updateProcessor = BehaviorProcessor.create();

        statePublisher = updateProcessor.map(Update::getState);

        statePublisher.zipWith(actionProcessor, updater::apply)
                .startWith(Update.of(initialState))
                .subscribe(updateProcessor);

        commandPublisher = updateProcessor
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands);

        start();
    }

    public Subscriber<Object> createActionSubscriber() {
        final PublishProcessor<Object> actionProcessor = PublishProcessor.create();
        actionProcessor.forEach(this::dispatch);
        return actionProcessor;
    }

    public Publisher<STATE> getStatePublisher() {
        return statePublisher;
    }

    public void register(Driver driver) {
        commandPublisher.subscribe(driver.getCommandSubscriber());
        Flowable.fromPublisher(driver.getActionPublisher()).forEach(this::dispatch);

    }

    public synchronized void start() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor(THREAD_FACTORY);
            executor.execute(() -> {
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        actionProcessor.offer(queue.take());
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public synchronized void stop() {
        if (executor != null) {
            executor.shutdownNow();
            try {
                if (! executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Unable to stop main loop");
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException("Unable to stop main loop", e);
            }
            executor = null;
        }
    }

    private boolean dispatch(Object action) {
        return queue.offer(action);
    }

}