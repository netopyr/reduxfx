package com.netopyr.reduxfx.differ;

import com.netopyr.reduxfx.differ.patches.AppendPatch;
import com.netopyr.reduxfx.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.differ.patches.RemovePatch;
import com.netopyr.reduxfx.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.differ.patches.UpdateRootPatch;
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
        final Vector<Patch> patches =
                a.isEmpty() ?
                        b.isEmpty() ? Vector.empty() : Vector.of(new UpdateRootPatch(Vector.empty(), b.get()))
                        :
                        b.isEmpty() ? Vector.of(new RemovePatch(Vector.empty())) : doDiff(Vector.empty(), a.get(), b.get());
        LOG.trace("Diff:\na:\n{}\nb:\n{}\nresult:\n{}", a, b, patches);
        return patches;
    }

    private static Vector<Patch> doDiff(Vector<Object> path, VNode a, VNode b) {
        Objects.requireNonNull(a, "First node must not be null");
        Objects.requireNonNull(b, "Second node must not be null");

        // TODO Wie hiermit umgehen, wenn named child?
        if (a.getNodeClass() != b.getNodeClass()) {
            return Vector.of(new ReplacePatch(path, b));
        }

        return diffAttributes(path, a, b)
                .appendAll(diffChildren(path, a, b))
                .appendAll(diffNamedChildren(path, a, b));
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

    private static Vector<Patch> diffChildren(Vector<Object> path, VNode a, VNode b) {

        final Array<VNode> aChildren = a.getChildren();
        final Array<VNode> bChildren = b.getChildren();
        final int nA = aChildren.length();
        final int nB = bChildren.length();
        final int n = Math.min(nA, nB);

        Vector<Patch> result = Vector.empty();

        for (int i = 0; i < n; i++) {
            final VNode aChild = aChildren.get(i);
            final VNode bChild = bChildren.get(i);
            result = aChild.getNodeClass() != bChild.getNodeClass()? result.append(new ReplacePatch(path.append(i), bChild))
                    : result.appendAll(doDiff(path.append(i), aChild, bChild));
        }

        for (int i = n; i < nA; i++) {
            result = result.append(new RemovePatch(path.append(i)));
        }

        for (int i = n; i < nB; i++) {
            result = result.append(new AppendPatch(path.append(i), bChildren.get(i)));
        }

        return result;
    }

    private static Vector<Patch> diffNamedChildren(Vector<Object> path, VNode oldNode, VNode newNode) {

        final Map<String, VProperty> oldChildren = oldNode.getNamedChildren();
        final Map<String, VProperty> newChildren = newNode.getNamedChildren();

        final Vector<Patch> updatedChildren = Vector.ofAll(oldChildren
                .filter(oldChild -> newChildren.containsKey(oldChild._1))
                .map(oldChild -> Tuple.of(oldChild._1, oldChild._2, newChildren.get(oldChild._1).get()))
                .flatMap(tuple -> {
                    final VNode a = (VNode) tuple._2.getValue();
                    final VNode b = (VNode) tuple._3.getValue();
                    return (a.getNodeClass() != b.getNodeClass()) ?
                            Vector.of(new AttributesPatch(path, tuple._1, tuple._3))
                            : doDiff(path.append(tuple._1), a, b);
                })
        );
        final Seq<Patch> removedChildren = oldChildren.removeAll(newChildren.keySet())
                .map(tuple -> new AttributesPatch(path, tuple._1, new VProperty(null, Option.none(), Option.none())));
        final Seq<Patch> addedChildren = newChildren.removeAll(oldChildren.keySet())
                .map(tuple -> new AttributesPatch(path, tuple._1, tuple._2));
        return updatedChildren.appendAll(removedChildren).appendAll(addedChildren);
    }
}
