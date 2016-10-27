package com.netopyr.reduxfx;

import com.netopyr.reduxfx.differ.Differ;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.patcher.Patcher;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javaslang.Function2;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ReduxFX  {

    private static final Logger LOG = LoggerFactory.getLogger(ReduxFX.class);

    private ReduxFX() {}

    public static <STATE, ACTION> void start(STATE initialState, Function2<STATE, ACTION, STATE> update, Function<STATE, VNode<ACTION>> view, Stage stage) {
        final StackPane root = new StackPane();
        root.setMinWidth(Region.USE_PREF_SIZE);
        root.setMinHeight(Region.USE_PREF_SIZE);
        root.setMaxWidth(Region.USE_PREF_SIZE);
        root.setMaxHeight(Region.USE_PREF_SIZE);

        start(initialState, update, view, root);

        final Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public static <STATE, ACTION> void start(STATE initialState, Function2<STATE, ACTION, STATE> update, Function<STATE, VNode<ACTION>> view, Group root) {
        doStart(initialState, update, view, root);
    }

    public static <STATE, ACTION> void start(STATE initialState, Function2<STATE, ACTION, STATE> update, Function<STATE, VNode<ACTION>> view, Pane root) {
        doStart(initialState, update, view, root);
    }

    private static <STATE, ACTION> void doStart(STATE initialState, BiFunction<STATE, ACTION, STATE> update, Function<STATE, VNode<ACTION>> view, Parent root) {
        LOG.info("Starting ReduxFX");

        final Subject<ACTION, ACTION> actionStream = PublishSubject.create();

        final Observable<STATE> stateStream = actionStream.scan(initialState, update::apply);

        final Observable<Option<VNode<ACTION>>> vScenegraphStream = stateStream
                .map(view::apply)
                .map(Option::of)
                .startWith(Option.<VNode<ACTION>>none());

        final Observable<Vector<Patch>> patchStream = vScenegraphStream.zipWith(vScenegraphStream.skip(1), Differ::diff);

        final Patcher<ACTION> patcher = new Patcher<>(actionStream::onNext);

        vScenegraphStream
                .zipWith(patchStream, NodePatchPair::new)
                .forEach(pair -> patcher.patch(root, pair.node, pair.patches));
    }

    private static class NodePatchPair<ACTION> {
        private final Option<VNode<ACTION>> node;
        private final Vector<Patch> patches;

        private NodePatchPair(Option<VNode<ACTION>> node, Vector<Patch> patches) {
            this.node = node;
            this.patches = patches;
        }
    }
}
