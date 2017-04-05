package com.netopyr.reduxfx.impl.differ;

import com.netopyr.reduxfx.impl.differ.patches.AppendPatch;
import com.netopyr.reduxfx.impl.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.impl.differ.patches.Patch;
import com.netopyr.reduxfx.impl.differ.patches.RemovePatch;
import com.netopyr.reduxfx.impl.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.impl.differ.patches.SetChildrenPatch;
import com.netopyr.reduxfx.impl.differ.patches.SetSingleChildPatch;
import com.netopyr.reduxfx.impl.differ.patches.UpdateRootPatch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.Tuple;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Differ {

    private static final Logger LOG = LoggerFactory.getLogger(Differ.class);

    public static Vector<Patch> diff(Option<VNode> a, Option<VNode> b) {
        if (!a.isEmpty() && b.isEmpty()) {
            LOG.error("Tried to remove root node");
            return Vector.empty();
        }

        final Vector<Patch> patches =
                a.isEmpty() ?
                        b.isEmpty() ? Vector.empty() : Vector.of(new UpdateRootPatch(b.get()))
                        : doDiff(Vector.empty(), a.get(), b.get());
        LOG.trace("Diff:\na:\n{}\nb:\n{}\nresult:\n{}", a, b, patches);
        return patches;
    }

    private static Vector<Patch> doDiff(Vector<Object> path, VNode a, VNode b) {
        Objects.requireNonNull(a, "First node must not be null");
        Objects.requireNonNull(b, "Second node must not be null");

        return diffAttributes(path, a, b)
                .appendAll(diffChildrenMaps(path, a, b))
                .appendAll(diffSingleChildMaps(path, a, b));
    }

    private static Vector<Patch> diffAttributes(Vector<Object> path, VNode a, VNode b) {
        final Map<String, VProperty> removedProperties = a.getProperties().removeAll(b.getProperties().keySet()).map((key, value) -> Tuple.of(key, new VProperty(key, Option.none(), Option.none())));
        final Map<String, VProperty> updatedProperties = b.getProperties().filter(propertyB -> !Option.of(propertyB._2).equals(a.getProperties().get(propertyB._1)));
        final Map<String, VProperty> diffProperties = removedProperties.merge(updatedProperties);

        final Map<VEventType, Option<VEventHandler>> removedEventHandlers = a.getEventHandlers().removeAll(b.getEventHandlers().keySet()).map((key, value) -> Tuple.of(key, Option.none()));
        final Map<VEventType, Option<VEventHandler>> updatedEventHandlers = b.getEventHandlers().filter(handlerB -> !Objects.equals(Option.of(handlerB._2), a.getEventHandlers().get(handlerB._1))).map((key, value) -> Tuple.of(key, Option.of(value)));
        final Map<VEventType, Option<VEventHandler>> diffEventHandlers = removedEventHandlers.merge(updatedEventHandlers);

        return diffProperties.isEmpty() && diffEventHandlers.isEmpty() ? Vector.empty()
                : Vector.of(new AttributesPatch(path, diffProperties, diffEventHandlers));
    }

    private static Seq<Patch> diffChildrenMaps(Vector<Object> path, VNode a, VNode b) {

        final Map<String, Array<VNode>> oldChildrenMap = a.getChildrenMap();
        final Map<String, Array<VNode>> newChildrenMap = b.getChildrenMap();

        final Seq<Patch> updatedChildren = oldChildrenMap
                .filter(oldChildren -> newChildrenMap.containsKey(oldChildren._1))
                .map(oldChildren -> Tuple.of(oldChildren._1, oldChildren._2, newChildrenMap.get(oldChildren._1).get()))
                .flatMap(tuple -> {
                    final Vector<Object> currentPath = path.append(tuple._1);
                    final Array<VNode> oldChildren = tuple._2;
                    final Array<VNode> newChildren = tuple._3;

                    final int nA = oldChildren.length();
                    final int nB = newChildren.length();
                    final int n = Math.min(nA, nB);

                    Vector<Patch> result = Vector.empty();

                    for (int i = 0; i < n; i++) {
                        final VNode aChild = oldChildren.get(i);
                        final VNode bChild = newChildren.get(i);
                        result = aChild.getNodeClass() != bChild.getNodeClass() ? result.append(new ReplacePatch(currentPath, i, bChild))
                                : result.appendAll(doDiff(currentPath.append(i), aChild, bChild));
                    }

                    for (int i = n; i < nA; i++) {
                        result = result.append(new RemovePatch(currentPath, i));
                    }

                    for (int i = n; i < nB; i++) {
                        result = result.append(new AppendPatch(currentPath, newChildren.get(i)));
                    }

                    return result;
                });

        final Seq<Patch> removedChildren = oldChildrenMap.removeAll(newChildrenMap.keySet())
                .map(tuple -> new SetChildrenPatch(path, tuple._1, Array.empty()));
        final Seq<Patch> addedChildren = newChildrenMap.removeAll(oldChildrenMap.keySet())
                .map(tuple -> new SetChildrenPatch(path, tuple._1, tuple._2));
        return updatedChildren.appendAll(removedChildren).appendAll(addedChildren);
    }

    private static Vector<Patch> diffSingleChildMaps(Vector<Object> path, VNode oldNode, VNode newNode) {

        final Map<String, Option<VNode>> oldChildren = oldNode.getSingleChildMap();
        final Map<String, Option<VNode>> newChildren = newNode.getSingleChildMap();

        final Vector<Patch> updatedChildren = Vector.ofAll(oldChildren
                .filter(oldChild -> newChildren.containsKey(oldChild._1))
                .map(oldChild -> Tuple.of(oldChild._1, oldChild._2, newChildren.get(oldChild._1).get()))
                .flatMap(tuple -> {
                    final String key = tuple._1;
                    final Option<VNode> oldValue = tuple._2;
                    final Option<VNode> newValue = tuple._3;

                    if (oldValue.isEmpty() && newValue.isEmpty()) {
                        return Vector.empty();
                    }
                    if (newValue.isEmpty()) {
                        return Vector.of(new SetSingleChildPatch(path, key, Option.none()));
                    }
                    if (oldValue.isEmpty() || ! oldValue.get().getNodeClass().equals(newValue.get().getNodeClass())) {
                        return Vector.of(new SetSingleChildPatch(path, key, newValue));
                    }
                    return doDiff(path.append(key), oldValue.get(), newValue.get());
                })
        );
        final Seq<Patch> removedChildren = oldChildren.removeAll(newChildren.keySet())
                .map(tuple -> new SetSingleChildPatch(path, tuple._1, Option.none()));
        final Seq<Patch> addedChildren = newChildren.removeAll(oldChildren.keySet())
                .map(tuple -> new SetSingleChildPatch(path, tuple._1, tuple._2));
        return updatedChildren.appendAll(removedChildren).appendAll(addedChildren);
    }
}
