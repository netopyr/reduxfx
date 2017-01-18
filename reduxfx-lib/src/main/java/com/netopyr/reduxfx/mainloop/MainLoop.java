package com.netopyr.reduxfx.mainloop;

import com.netopyr.reduxfx.differ.Differ;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.patcher.Patcher;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.VNode;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import javafx.application.Platform;
import javafx.scene.Parent;
import javaslang.Tuple;
import javaslang.collection.Vector;
import javaslang.control.Option;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MainLoop {

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        final Thread thread = new Thread(runnable, "ReduxFXLoop-" + THREAD_COUNTER.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    };

    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
    private final Subject<Object> actionSubject = PublishSubject.create();
    private final Subject<Command> commandObservable = PublishSubject.create();

    private ExecutorService executor;

    public Observable<Command> getCommandObservable() {
        return commandObservable;
    }

    public <STATE> MainLoop(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Parent root
    ) {
        final Subject<Update<STATE>> updateSubject = BehaviorSubject.create();
        actionSubject
                .scan(Update.of(initialState), (update, action) -> updater.apply(update.getState(), action))
                .subscribe(updateSubject);

        final Subject<Option<VNode>> vScenegraphSubject = ReplaySubject.createWithSize(2);
        updateSubject.map(Update::getState)
                .map(view::apply)
                .map(Option::of)
                .startWith(Option.<VNode>none())
                .subscribe(vScenegraphSubject);

        final Observable<Vector<Patch>> patchObservable = vScenegraphSubject.zipWith(vScenegraphSubject.skip(1), Differ::diff);

        final Patcher patcher = new Patcher(this::dispatch);
        vScenegraphSubject
                .zipWith(patchObservable, Tuple::of)
                .forEach(tuple -> {
                    if (Platform.isFxApplicationThread()) {
                        patcher.patch(root, tuple._1, tuple._2);
                    } else {
                        Platform.runLater(() -> patcher.patch(root, tuple._1, tuple._2));
                    }
                });

        updateSubject
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands)
                .subscribe(commandObservable);

        start();
    }

    public synchronized void start() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor(THREAD_FACTORY);
            executor.execute(() -> {
                try {
                    while (true) {
                        actionSubject.onNext(queue.take());
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

    public boolean dispatch(Object action) {
        return queue.offer(action);
    }

}
