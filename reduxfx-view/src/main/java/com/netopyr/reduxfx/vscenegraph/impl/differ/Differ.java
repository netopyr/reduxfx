package com.netopyr.reduxfx.vscenegraph.impl.differ;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.Factory;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.AppendPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.Patch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.RemovePatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.SetChildrenPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.SetSingleChildPatch;
import com.netopyr.reduxfx.vscenegraph.impl.differ.patches.UpdateRootPatch;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VProperty.Phase;
import io.vavr.Tuple;
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Differ {

    private static final Logger LOG = LoggerFactory.getLogger(Differ.class);

    private Differ() {}

    public static Map<Phase, Vector<Patch>> diff(Option<VNode> a, Option<VNode> b) {
        if (!a.isEmpty() && b.isEmpty()) {
            LOG.error("Tried to remove root node");
            return HashMap.empty();
        }

        final Map<Phase, Vector<Patch>> patches;
        if (a.isEmpty()) {
            patches = b.isEmpty() ? HashMap.empty() : HashMap.of(Phase.STRUCTURE, Vector.of(new UpdateRootPatch(b.get())));
        } else {
            patches = doDiff(Vector.empty(), a.get(), b.get());
        }

        LOG.trace("Diff:\na:\n{}\nb:\n{}\nresult:\n{}", a, b, patches);
        return patches;
    }

    private static Map<Phase, Vector<Patch>> doDiff(Vector<Object> path, VNode a, VNode b) {
        Objects.requireNonNull(a, "First node must not be null");
        Objects.requireNonNull(b, "Second node must not be null");

        return diffAttributes(path, a, b)
                .merge(diffSingleChildMaps(path, a, b), Vector::appendAll)
                .merge(diffChildrenMaps(path, a, b), Vector::appendAll);
    }

    private static Map<Phase, Vector<Patch>> diffAttributes(Vector<Object> path, VNode a, VNode b) {
        final Map<String, VProperty> removedProperties = a.getProperties().removeAll(b.getProperties().keySet()).map((key, value) -> Tuple.of(key, Factory.property(value.getPhase(), key)));
        final Map<String, VProperty> updatedProperties = b.getProperties().filter(propertyB -> !Option.of(propertyB._2).equals(a.getProperties().get(propertyB._1)));

        final Map<VEventType, Option<VEventHandler>> removedEventHandlers = a.getEventHandlers().removeAll(b.getEventHandlers().keySet()).map((key, value) -> Tuple.of(key, Option.none()));
        final Map<VEventType, Option<VEventHandler>> updatedEventHandlers = b.getEventHandlers().filter(handlerB -> !Objects.equals(Option.of(handlerB._2), a.getEventHandlers().get(handlerB._1))).map((key, value) -> Tuple.of(key, Option.of(value)));
        final Map<VEventType, Option<VEventHandler>> diffEventHandlers = removedEventHandlers.merge(updatedEventHandlers);

        return removedProperties.merge(updatedProperties)
                .groupBy(tuple -> tuple._2.getPhase())
                .map((phase, properties) ->
                        Tuple.of(phase, Vector.of(
                                new AttributesPatch(
                                        path,
                                        properties,
                                        phase == Phase.DEFAULT ? diffEventHandlers : HashMap.empty()
                                )
                        ))
                );
    }

    private static Map<Phase, Vector<Patch>> diffChildrenMaps(Vector<Object> path, VNode a, VNode b) {

        final Map<String, Array<VNode>> oldChildrenMap = a.getChildrenMap();
        final Map<String, Array<VNode>> newChildrenMap = b.getChildrenMap();

        final Map<Phase, Vector<Patch>> updatedChildren = oldChildrenMap
                .filter(oldChildren -> newChildrenMap.containsKey(oldChildren._1))
                .map(oldChildren -> Tuple.of(oldChildren._1, oldChildren._2, newChildrenMap.get(oldChildren._1).get()))
                .map(tuple -> {
                    final Vector<Object> currentPath = path.append(tuple._1);
                    final Array<VNode> oldChildren = tuple._2;
                    final Array<VNode> newChildren = tuple._3;

                    final int nA = oldChildren.length();
                    final int nB = newChildren.length();
                    final int n = Math.min(nA, nB);

                    Map<Phase, Vector<Patch>> result = HashMap.empty();

                    for (int i = 0; i < n; i++) {
                        final VNode aChild = oldChildren.get(i);
                        final VNode bChild = newChildren.get(i);
                        result = aChild.getNodeClass() != bChild.getNodeClass() ? result.merge(HashMap.of(Phase.STRUCTURE, Vector.of(new ReplacePatch(currentPath, i, bChild))), Vector::appendAll)
                                : result.merge(doDiff(currentPath.append(i), aChild, bChild), Vector::appendAll);
                    }

                    if (nA > n) {
                        result = result.merge(HashMap.of(Phase.STRUCTURE, Vector.of(new RemovePatch(currentPath, n, nA))), Vector::appendAll);
                    }

                    for (int i = n; i < nB; i++) {
                        result = result.merge(HashMap.of(Phase.STRUCTURE, Vector.of(new AppendPatch(currentPath, newChildren.get(i)))), Vector::appendAll);
                    }

                    return result;
                })
                .fold(HashMap.empty(), (map1, map2) -> map1.merge(map2, Vector::appendAll));

        final Seq<Patch> removedChildren = oldChildrenMap.removeAll(newChildrenMap.keySet())
                .map(tuple -> new SetChildrenPatch(path, tuple._1, Array.empty()));
        final Seq<Patch> addedChildren = newChildrenMap.removeAll(oldChildrenMap.keySet())
                .map(tuple -> new SetChildrenPatch(path, tuple._1, tuple._2));
        return updatedChildren
                .merge(HashMap.of(Phase.STRUCTURE, removedChildren.toVector()), Vector::appendAll)
                .merge(HashMap.of(Phase.STRUCTURE, addedChildren.toVector()), Vector::appendAll);
    }

    private static Map<Phase, Vector<Patch>> diffSingleChildMaps(Vector<Object> path, VNode oldNode, VNode newNode) {

        final Map<String, Option<VNode>> oldChildren = oldNode.getSingleChildMap();
        final Map<String, Option<VNode>> newChildren = newNode.getSingleChildMap();

        final Map<Phase, Vector<Patch>> updatedChildren = oldChildren
                .filter(oldChild -> newChildren.containsKey(oldChild._1))
                .map(oldChild -> Tuple.of(oldChild._1, oldChild._2, newChildren.get(oldChild._1).get()))
                .map(tuple -> {
                    final String key = tuple._1;
                    final Option<VNode> oldValue = tuple._2;
                    final Option<VNode> newValue = tuple._3;

                    if (oldValue.isEmpty() && newValue.isEmpty()) {
                        return HashMap.<Phase, Vector<Patch>>empty();
                    }
                    if (newValue.isEmpty()) {
                        return HashMap.of(Phase.STRUCTURE, Vector.<Patch>of(new SetSingleChildPatch(path, key, Option.none())));
                    }
                    if (oldValue.isEmpty() || !oldValue.get().getNodeClass().equals(newValue.get().getNodeClass())) {
                        return HashMap.of(Phase.STRUCTURE, Vector.<Patch>of(new SetSingleChildPatch(path, key, newValue)));
                    }
                    return doDiff(path.append(key), oldValue.get(), newValue.get());
                })
                .fold(HashMap.empty(), (map1, map2) -> map1.merge(map2, Vector::appendAll));
        final Seq<Patch> removedChildren = oldChildren.removeAll(newChildren.keySet())
                .map(tuple -> new SetSingleChildPatch(path, tuple._1, Option.none()));
        final Seq<Patch> addedChildren = newChildren.removeAll(oldChildren.keySet())
                .map(tuple -> new SetSingleChildPatch(path, tuple._1, tuple._2));
        return updatedChildren
                .merge(HashMap.of(Phase.STRUCTURE, removedChildren.toVector()), Vector::appendAll)
                .merge(HashMap.of(Phase.STRUCTURE, addedChildren.toVector()), Vector::appendAll);
    }
}
