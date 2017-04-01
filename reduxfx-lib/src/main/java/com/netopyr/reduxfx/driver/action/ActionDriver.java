package com.netopyr.reduxfx.driver.action;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.driver.action.command.DispatchActionCommand;
import com.netopyr.reduxfx.updater.Command;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionDriver implements Driver {

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        final Thread thread = new Thread(runnable, "ActionDriver-" + THREAD_COUNTER.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    };

    private final ExecutorService executorService = Executors.newCachedThreadPool(THREAD_FACTORY);
    private final FlowableProcessor<Command> commandSubscriber = PublishProcessor.create();

    private final FlowableProcessor<Object> actions = PublishProcessor.create();


    public ActionDriver() {
        commandSubscriber
                .filter(command -> command instanceof DispatchActionCommand)
                .forEach(command ->
                        executorService.submit(() -> actions.onNext(((DispatchActionCommand)command).getAction()))
                );
    }


    @Override
    public Subscriber<Command> getCommandSubscriber() {
        return commandSubscriber;
    }

    @Override
    public Publisher<?> getActionPublisher() {
        return actions;
    }
}
