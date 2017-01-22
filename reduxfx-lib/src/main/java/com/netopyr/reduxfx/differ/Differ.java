package com.netopyr.reduxfx.differ;

import com.netopyr.reduxfx.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.differ.patches.InsertPatch;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.differ.patches.RemovePatch;
import com.netopyr.reduxfx.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.Tuple;
import javaslang.collection.Array;
import javaslang.collection.Map;
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
                        b.isEmpty() ? Vector.empty() : Vector.of(new InsertPatch(0, b.get()))
                        :
                        b.isEmpty() ? Vector.of(new RemovePatch(0)) : doDiff(a.get(), b.get(), 0);
        LOG.trace("Diff:\na:\n{}\nb:\n{}\nresult:\n{}", a, b, patches);
        return patches;
    }

    private static Vector<Patch> doDiff(VNode a, VNode b, int index) {
        if (a == null ? b == null : a.equals(b)) {
            return Vector.empty();
        }

        if (a.getNodeClass() != b.getNodeClass()) {
            return Vector.of(new ReplacePatch(index, b));
        }

        Vector<Patch> result = diffAttributes(index, a, b);

        final Array<VNode> aChildren = a.getChildren();
        final Array<VNode> bChildren = b.getChildren();
        final int nA = aChildren.length();
        final int nB = bChildren.length();
        final int n = Math.min(nA, nB);
        int childIndex = index + 1;

        for (int i = 0; i < n; i++) {
            result = result.appendAll(doDiff(aChildren.get(i), bChildren.get(i), childIndex));
            childIndex += aChildren.get(i).getSize();
        }

        for (int i = n; i < nA; i++) {
            result = result.append(new RemovePatch(childIndex));
            childIndex += aChildren.get(i).getSize();
        }

        for (int i = n; i < nB; i++) {
            result = result.append(new InsertPatch(index, bChildren.get(i)));
        }

        return result;
    }

    private static Vector<Patch> diffAttributes(int index, VNode a, VNode b) {
        final Map<String, VProperty> removedProperties = a.getProperties().filter(propertyA -> !b.getProperties().containsKey(propertyA._1)).map((key, value) -> Tuple.of(key, new VProperty(key, Option.none(), Option.none())));
        final Map<String, VProperty> updatedProperties = b.getProperties().filter(propertyB -> !Option.of(propertyB._2).equals(a.getProperties().get(propertyB._1)));
        final Map<String, VProperty> diffProperties = removedProperties.merge(updatedProperties);

        final Map<VEventType, Option<VEventHandler>> removedEventHandlers = a.getEventHandlers().filter(handlerA -> !b.getEventHandlers().containsKey(handlerA._1)).map((key, value) -> Tuple.of(key, Option.none()));
        final Map<VEventType, Option<VEventHandler>> updatedEventHandlers = b.getEventHandlers().filter(handlerB -> !Objects.equals(Option.of(handlerB._2), a.getEventHandlers().get(handlerB._1))).map((key, value) -> Tuple.of(key, Option.of(value)));
        final Map<VEventType, Option<VEventHandler>> diffEventHandlers = removedEventHandlers.merge(updatedEventHandlers);

        return diffProperties.isEmpty() && diffEventHandlers.isEmpty() ? Vector.empty()
                : Vector.of(new AttributesPatch(index, diffProperties, diffEventHandlers));
    }


}
