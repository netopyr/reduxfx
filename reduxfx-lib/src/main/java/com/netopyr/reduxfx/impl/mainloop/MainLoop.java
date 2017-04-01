package com.netopyr.reduxfx.impl.mainloop;

import com.netopyr.reduxfx.impl.differ.Differ;
import com.netopyr.reduxfx.impl.differ.patches.Patch;
import com.netopyr.reduxfx.impl.patcher.Patcher;
import com.netopyr.reduxfx.updater.Command;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.Stages;
import com.netopyr.reduxfx.vscenegraph.VNode;
import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javaslang.collection.Seq;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.reactivestreams.Publisher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

public class MainLoop {

    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger();
    private static final ThreadFactory THREAD_FACTORY = runnable -> {
        final Thread thread = new Thread(runnable, "ReduxFXLoop-" + THREAD_COUNTER.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    };

    private final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();
    private final FlowableProcessor<Object> actionsStream = PublishProcessor.create();
    private final FlowableProcessor<Command> commandsStream = PublishProcessor.create();

    private final Patcher patcher = new Patcher(this::dispatch);

    private ExecutorService executor;

    public static <STATE> MainLoop createStages(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        final Stages stages = new Stages(primaryStage);
        final Option<VNode> initialVNode = Option.of(Stages().children(Stage()));
        return new MainLoop(initialState, initialVNode, updater, view, stages);
    }

    public static <STATE> MainLoop createStage(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        return new MainLoop(initialState, Option.none(), updater, view, primaryStage);
    }

    public static <STATE> MainLoop create(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Stage primaryStage)
    {
        final Function<STATE, VNode> stageView = STATE -> Stage().scene(Scene().root(view.apply(STATE)));
        return new MainLoop(initialState, Option.none(), updater, stageView, primaryStage);
    }

    public static <STATE> MainLoop create(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Group group)
    {
        return new MainLoop(initialState, Option.none(), updater, view, group);
    }

    public static <STATE> MainLoop create(
            STATE initialState,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Pane pane)
    {
        return new MainLoop(initialState, Option.none(), updater, view, pane);
    }

    private <STATE> MainLoop(
            STATE initialState,
            Option<VNode> initialVNode,
            BiFunction<STATE, Object, Update<STATE>> updater,
            Function<STATE, VNode> view,
            Object javaFXRoot)
    {
        final FlowableProcessor<Update<STATE>> updatesStream = BehaviorProcessor.create();

        final Flowable<STATE> statesStream = updatesStream.map(Update::getState);
        statesStream.zipWith(actionsStream, updater::apply)
                .startWith(Update.of(initialState))
                .subscribe(updatesStream);

        final FlowableProcessor<Option<VNode>> vScenegraphsStream = PublishProcessor.create();

        final Flowable<Vector<Patch>> patchesStream = vScenegraphsStream.zipWith(vScenegraphsStream.skip(1), Differ::diff);

        vScenegraphsStream
                .zipWith(patchesStream, PatchParams::new)
                .forEach(params -> {
                    if (Platform.isFxApplicationThread()) {
                        patcher.patch(javaFXRoot, params.vRoot, params.patches);
                    } else {
                        Platform.runLater(() -> patcher.patch(javaFXRoot, params.vRoot, params.patches));
                    }
                });

        updatesStream.map(Update::getState)
                .map(view::apply)
                .map(Option::of)
                .startWith(initialVNode)
                .subscribe(vScenegraphsStream);


        updatesStream
                .map(Update::getCommands)
                .flatMapIterable(commands -> commands)
                .subscribe(commandsStream);

        start();
    }



    public Publisher<Command> getCommandPublisher() {
        return commandsStream;
    }

    public synchronized void start() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor(THREAD_FACTORY);
            executor.execute(() -> {
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        actionsStream.onNext(queue.take());
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


    private static class PatchParams {
        private final Option<VNode> vRoot;
        private final Seq<Patch> patches;

        private PatchParams(Option<VNode> vRoot, Seq<Patch> patches) {
            this.vRoot = vRoot;
            this.patches = patches;
        }
    }

}
