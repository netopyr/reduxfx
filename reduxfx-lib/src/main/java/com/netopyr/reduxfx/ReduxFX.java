package com.netopyr.reduxfx;

import com.netopyr.reduxfx.patcher.Differ;
import com.netopyr.reduxfx.patcher.Patcher;
import com.netopyr.reduxfx.patcher.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javaslang.Tuple2;
import javaslang.collection.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.ReplaySubject;

public class ReduxFX {

    private static final Logger LOG = LoggerFactory.getLogger(ReduxFX.class);

    public static <STATE, ACTION> void start(STATE initialState, Reducer<STATE, ACTION> reducer, View<STATE, ACTION> view, Group root) {
        doStart(initialState, reducer, view, root);
    }

    public static <STATE, ACTION> void start(STATE initialState, Reducer<STATE, ACTION> reducer, View<STATE, ACTION> view, Pane root) {
        doStart(initialState, reducer, view, root);
    }

    private static VNode addRoot(VNode vNode) {
        return VScenegraphFactory.Root(vNode);
    }

    @SuppressWarnings("unchecked")
    private static <STATE, ACTION> void doStart(STATE initialState, Reducer<STATE, ACTION> reducer, View<STATE, ACTION> view, Node root) {
        LOG.info("Starting ReduxFX");

        final ReplaySubject<ACTION> actionStream = ReplaySubject.create();

        final Observable<STATE> stateStream = actionStream.scan(initialState, reducer::reduce);

        final Observable<VNode> vNodeStream = stateStream
                .map(view::view)
                .map(ReduxFX::addRoot)
                .startWith(VScenegraphFactory.Root());

        final Observable<Vector<Patch>> patchStream = vNodeStream.zipWith(vNodeStream.skip(1), Differ::diff);
        vNodeStream.zipWith(patchStream, Tuple2::new).forEach(entry -> Patcher.patch(root, entry._1, entry._2, item -> actionStream.onNext((ACTION) item)));
    }
}
