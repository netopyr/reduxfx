package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.vscenegraph.impl.differ.Differ;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.Patcher;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.reactivex.Flowable;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.function.Function;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ReduxFXView<S> {

    public static <S> ReduxFXView<S> createStages(
            ReduxFXStore<S> store,
            Function<S, VNode> view,
            Stage primaryStage) {
        final Stages stages = new Stages(primaryStage);
        final Option<VNode> initialVNode = Option.of(Stages().children(Stage()));
        return new ReduxFXView<>(store, view, stages, initialVNode);
    }

    public static <S> ReduxFXView<S> createStage(
            ReduxFXStore<S> store,
            Function<S, VNode> view,
            Stage primaryStage) {
        return new ReduxFXView<>(store, view, primaryStage, Option.none());
    }

    public static <S> ReduxFXView<S> create(
            ReduxFXStore<S> store,
            Function<S, VNode> view,
            Stage primaryStage) {
        final Function<S, VNode> stageView = state -> Stage().scene(Scene().root(view.apply(state)));
        return new ReduxFXView<>(store, stageView, primaryStage, Option.none());
    }

    public static <S> ReduxFXView<S> create(
            ReduxFXStore<S> store,
            Function<S, VNode> view,
            Group group) {
        return new ReduxFXView<>(store, view, group, Option.none());
    }

    public static <S> ReduxFXView<S> create(
            ReduxFXStore<S> store,
            Function<S, VNode> view,
            Pane pane) {
        return new ReduxFXView<>(store, view, pane, Option.none());
    }

    private ReduxFXView(
            ReduxFXStore<S> store,
            Function<S, VNode> view,
            Object javaFXRoot,
            Option<VNode> initialVNode) {
        final Flowable<Option<VNode>> vScenegraphStream =
                Flowable.fromPublisher(store)
                        .map(view::apply)
                        .map(Option::of)
                        .startWith(initialVNode);

        final Flowable<Map<VProperty.Phase, Vector<Patch>>> patchesStream =
                vScenegraphStream.zipWith(vScenegraphStream.skip(1), Differ::diff);

        final Flowable<PatchParams> paramsStream = vScenegraphStream.zipWith(patchesStream, PatchParams::new);

        paramsStream.forEach(
                params -> {
                    if (Platform.isFxApplicationThread()) {
                        Patcher.patch(store::dispatch, javaFXRoot, params.vRoot, params.patches);
                    } else {
                        Platform.runLater(() -> Patcher.patch(store::dispatch, javaFXRoot, params.vRoot, params.patches));
                    }
                }
        );
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
