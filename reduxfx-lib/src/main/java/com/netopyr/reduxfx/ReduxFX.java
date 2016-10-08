package com.netopyr.reduxfx;

import com.netopyr.reduxfx.differ.Differ;
import com.netopyr.reduxfx.patcher.Env;
import com.netopyr.reduxfx.patcher.Patcher;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VScenegraphFactory;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javaslang.collection.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.ReplaySubject;

import java.util.function.Consumer;

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

        final Env env = new Env();

        final ReplaySubject<ACTION> actionStream = ReplaySubject.create();
        final Consumer<ACTION> dispatcher = actionStream::onNext;

        final Observable<STATE> stateStream = actionStream.scan(initialState, reducer::reduce);

        final Observable<VNode> vNodeStream = stateStream
                .map(view::view)
                .map(ReduxFX::addRoot)
                .startWith(VScenegraphFactory.Root());

        final Observable<Vector<Patch>> patchStream = vNodeStream.zipWith(vNodeStream.skip(1), Differ::diff);

        final Patcher<ACTION> patcher = new Patcher<>(actionStream::onNext);

        vNodeStream
                .zipWith(patchStream, NodePatchPair::new)
                .forEach(pair -> {
                    env.cleanup(pair.node);
                    patcher.patch(root, pair.node, pair.patches);
                });
    }

    private static class NodePatchPair {
        private final VNode node;
        private final Vector<Patch> patches;

        private NodePatchPair(VNode node, Vector<Patch> patches) {
            this.node = node;
            this.patches = patches;
        }
    }
}
