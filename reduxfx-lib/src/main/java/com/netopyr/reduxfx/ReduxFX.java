package com.netopyr.reduxfx;

import com.netopyr.reduxfx.differ.Differ;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.patcher.Patcher;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class ReduxFX {

    private static final Logger LOG = LoggerFactory.getLogger(ReduxFX.class);



    public <STATE, ACTION> void start(STATE initialState, Reducer<STATE, ACTION> reducer, View<STATE, ACTION> view, Group root) {
        doStart(initialState, reducer, view, root);
    }

    public <STATE, ACTION> void start(STATE initialState, Reducer<STATE, ACTION> reducer, View<STATE, ACTION> view, Pane root) {
        doStart(initialState, reducer, view, root);
    }



//    public void registerAccessor(
//            Class<? extends Node> nodeClass,
//            String propertyName,
//            Accessor accessor
//    ) {
//        accessors = Accessors.register(accessors, nodeClass, propertyName, accessor);
//    }



    @SuppressWarnings("unchecked")
    private <STATE, ACTION> void doStart(STATE initialState, Reducer<STATE, ACTION> reducer, View<STATE, ACTION> view, Parent root) {
        LOG.info("Starting ReduxFX");

        final Subject<ACTION, ACTION> actionStream = PublishSubject.create();

        final Observable<STATE> stateStream = actionStream.scan(initialState, reducer::reduce);

        final Observable<Option<VNode<ACTION>>> vScenegraphStream = stateStream
                .map(view::view)
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
