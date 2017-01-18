package com.netopyr.reduxfx.differ;

import com.netopyr.reduxfx.differ.patches.AttributesPatch;
import com.netopyr.reduxfx.differ.patches.InsertPatch;
import com.netopyr.reduxfx.differ.patches.Patch;
import com.netopyr.reduxfx.differ.patches.RemovePatch;
import com.netopyr.reduxfx.differ.patches.ReplacePatch;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.Tuple;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.collection.Vector;
import javaslang.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        final Map<String, VProperty<?>> aProperties = a.getProperties().toMap(property -> Tuple.of(property.getName(), property));
        final Map<String, VProperty<?>> bProperties = b.getProperties().toMap(property -> Tuple.of(property.getName(), property));
        final Map<String, VProperty<?>> removedProperties = aProperties.filter(propertyA -> !bProperties.containsKey(propertyA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<String, VProperty<?>> updatedProperties = bProperties.filter(propertyB -> !Option.of(propertyB._2).equals(aProperties.get(propertyB._1)));
        final Map<String, VProperty<?>> diffProperties = removedProperties.merge(updatedProperties);

        final Map<VEventType, VEventHandlerElement<?>> aEventHandlers = a.getEventHandlers().toMap(eventHandler -> Tuple.of(eventHandler.getType(), eventHandler));
        final Map<VEventType, VEventHandlerElement<?>> bEventHandlers = a.getEventHandlers().toMap(eventHandler -> Tuple.of(eventHandler.getType(), eventHandler));
        final Map<VEventType, VEventHandlerElement<?>> removedEventHandlers = aEventHandlers.filter(handlerA -> !bEventHandlers.containsKey(handlerA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<VEventType, VEventHandlerElement<?>> updatedEventHandlers = bEventHandlers.filter(handlerB -> !Option.of(handlerB._2).equals(aEventHandlers.get(handlerB._1)));
        final Map<VEventType, VEventHandlerElement<?>> diffEventHandlers = removedEventHandlers.merge(updatedEventHandlers);

        return diffProperties.isEmpty() && diffEventHandlers.isEmpty() ? Vector.empty()
                : Vector.of(new AttributesPatch(index, diffProperties, diffEventHandlers));
    }


}
