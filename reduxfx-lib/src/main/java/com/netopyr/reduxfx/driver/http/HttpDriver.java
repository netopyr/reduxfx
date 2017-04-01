package com.netopyr.reduxfx.driver.http;

import com.netopyr.reduxfx.driver.Driver;
import com.netopyr.reduxfx.driver.http.command.HttpGetCommand;
import com.netopyr.reduxfx.driver.http.command.HttpPutCommand;
import com.netopyr.reduxfx.updater.Command;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import javaslang.control.Try;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class HttpDriver implements Driver {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        final Thread thread = new Thread(runnable, "HttpDriver-" + THREAD_COUNTER.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    };

    private final OkHttpClient client;

    private final ExecutorService executorService = Executors.newCachedThreadPool(THREAD_FACTORY);

    private final FlowableProcessor<Command> commandSubscriber = PublishProcessor.create();

    private final FlowableProcessor<Object> actions = PublishProcessor.create();

    public HttpDriver() {
        this.client = new OkHttpClient();

        commandSubscriber
                .filter(command -> command instanceof HttpGetCommand)
                .forEach(command -> httpGet((HttpGetCommand) command));

        commandSubscriber
                .filter(command -> command instanceof HttpPutCommand)
                .forEach(command -> httpPut((HttpPutCommand) command));
    }

    @Override
    public Publisher<?> getActionPublisher() {
        return actions;
    }

    @Override
    public Subscriber<Command> getCommandSubscriber() {
        return commandSubscriber;
    }

    private void httpGet(HttpGetCommand command) {
        final Headers.Builder headersBuilder = new Headers.Builder();
        command.getHeaders().forEach(tuple -> headersBuilder.add(tuple._1, tuple._2));
        final Request request = new Request.Builder()
                .url(command.getUrl())
                .headers(headersBuilder.build())
                .build();

        executorService.submit(() -> {
            final Try<String> result = Try.of(
                    () -> client.newCall(request).execute().body().string()
            );
            actions.onNext(command.getActionMapper().apply(result));
        });
    }

    private void httpPut(HttpPutCommand command) {
        final Headers.Builder headersBuilder = new Headers.Builder();
        command.getHeaders().forEach(tuple -> headersBuilder.add(tuple._1, tuple._2));
        final RequestBody body = RequestBody.create(JSON, command.getBody());
        final Request request = new Request.Builder()
                .put(body)
                .url(command.getUrl())
                .headers(headersBuilder.build())
                .build();

        executorService.submit(() -> {
            final Try<String> result = Try.of(
                    () -> client.newCall(request).execute().body().string()
            );
            command.getActionMapper().forEach(
                    mapper -> mapper.apply(result)
            );
        });
    }
}
