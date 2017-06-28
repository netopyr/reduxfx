package com.netopyr.reduxfx.driver.properties;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.driver.properties.command.LoadFileCommand;
import com.netopyr.reduxfx.updater.Command;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.Reader;
import java.nio.file.Files;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PropertiesDriver implements Driver {

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        final Thread thread = new Thread(runnable, "PropertiesDriver-" + THREAD_COUNTER.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    };

    private final ExecutorService executorService = Executors.newCachedThreadPool(THREAD_FACTORY);

    private final FlowableProcessor<Command> commandSubscriber = PublishProcessor.create();

    private final FlowableProcessor<Object> actions = PublishProcessor.create();

    public PropertiesDriver() {
        commandSubscriber
                .filter(command -> LoadFileCommand.class.equals(command.getClass()))
                .forEach(command -> loadFile((LoadFileCommand) command));
    }

    @Override
    public Publisher<?> getActionPublisher() {
        return actions;
    }

    @Override
    public Subscriber<Command> getCommandSubscriber() {
        return commandSubscriber;
    }

    private void loadFile(LoadFileCommand command) {
        executorService.submit(() -> {
            final Try<Map<String, String>> result = Try.of(() -> {
                final Properties properties = new Properties();
                try (final Reader reader = Files.newBufferedReader(command.getPath(), command.getCharset())) {
                    properties.load(reader);
                }
                return HashMap.ofEntries(
                        properties.entrySet().stream()
                                .filter(entry -> entry.getKey() instanceof String && entry.getValue() instanceof String)
                                .map(entry -> Tuple.of((String) entry.getKey(), (String) entry.getValue()))
                                .collect(Collectors.toList())
                );
            });
            actions.onNext(command.getActionMapper().apply(result));
        });
    }
}
