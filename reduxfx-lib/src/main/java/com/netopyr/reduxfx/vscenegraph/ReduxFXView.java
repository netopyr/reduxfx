package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.impl.differ.Differ;
import com.netopyr.reduxfx.impl.differ.patches.Patch;
import com.netopyr.reduxfx.impl.patcher.Patcher;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javaslang.collection.Map;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.Function;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ReduxFXView<STATE> {

    public static <STATE> ReduxFXView<STATE> createStages(
            Function<STATE, VNode> view,
            Stage primaryStage) {
        final Stages stages = new Stages(primaryStage);
        final Option<VNode> initialVNode = Option.of(Stages().children(Stage()));
        return new ReduxFXView<>(initialVNode, view, stages);
    }

    public static <STATE> ReduxFXView<STATE> createStage(
            Function<STATE, VNode> view,
            Stage primaryStage) {
        return new ReduxFXView<>(Option.none(), view, primaryStage);
    }

    public static <STATE> ReduxFXView<STATE> create(
            Function<STATE, VNode> view,
            Stage primaryStage) {
        final Function<STATE, VNode> stageView = STATE -> Stage().scene(Scene().root(view.apply(STATE)));
        return new ReduxFXView<>(Option.none(), stageView, primaryStage);
    }

    public static <STATE> ReduxFXView<STATE> create(
            Function<STATE, VNode> view,
            Group group) {
        return new ReduxFXView<>(Option.none(), view, group);
    }

    public static <STATE> ReduxFXView<STATE> create(
            Function<STATE, VNode> view,
            Pane pane) {
        return new ReduxFXView<>(Option.none(), view, pane);
    }

    private final Option<VNode> initialVNode;
    private final Function<STATE, VNode> view;
    private final Object javaFXRoot;

    private ReduxFXView(
            Option<VNode> initialVNode,
            Function<STATE, VNode> view,
            Object javaFXRoot) {
        this.initialVNode = initialVNode;
        this.view = view;
        this.javaFXRoot = javaFXRoot;
    }

    public void connect(Processor<Object, STATE> store) {
        connect(store, store);
    }

    public void connect(Subscriber<Object> actionStream, Publisher<STATE> statePublisher) {
        final Flowable<Option<VNode>> vScenegraphStream =
                Flowable.fromPublisher(statePublisher)
                        .map(view::apply)
                        .map(Option::of)
                        .startWith(initialVNode);

        final Flowable<Map<VProperty.Phase, Vector<Patch>>> patchesStream =
                vScenegraphStream.zipWith(vScenegraphStream.skip(1), Differ::diff);

        final Flowable<PatchParams> paramsStream = vScenegraphStream.zipWith(patchesStream, PatchParams::new);

        Flowable.create(
                emitter ->
                        paramsStream.forEach(
                                params -> {
                                    if (Platform.isFxApplicationThread()) {
                                        Patcher.patch(emitter::onNext, javaFXRoot, params.vRoot, params.patches);
                                    } else {
                                        Platform.runLater(() -> Patcher.patch(emitter::onNext, javaFXRoot, params.vRoot, params.patches));
                                    }
                                }
                        ),
                BackpressureStrategy.BUFFER
        ).subscribe(actionStream);
    }

    private static class PatchParams {
        private final Option<VNode> vRoot;
        private final Map<VProperty.Phase, Vector<Patch>> patches;

        private PatchParams(Option<VNode> vRoot, Map<VProperty.Phase, Vector<Patch>> patches) {
            this.vRoot = vRoot;
            this.patches = patches;
        }
    }

}
