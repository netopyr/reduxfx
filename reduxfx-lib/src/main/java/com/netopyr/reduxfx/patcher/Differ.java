package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.patcher.patches.AttributesPatch;
import com.netopyr.reduxfx.patcher.patches.InsertPatch;
import com.netopyr.reduxfx.patcher.patches.Patch;
import com.netopyr.reduxfx.patcher.patches.RemovePatch;
import com.netopyr.reduxfx.patcher.patches.ReplacePatch;
import com.netopyr.reduxfx.vscenegraph.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.VEventType;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VProperty;
import com.netopyr.reduxfx.vscenegraph.VPropertyType;
import javaslang.Tuple;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.collection.Vector;
import javaslang.control.Option;

public class Differ {

    public static <ACTION> Vector<Patch> diff(VNode<ACTION> a, VNode<ACTION> b) {
        return doDiff(a, b, 0);
    }

    private static <ACTION> Vector<Patch> doDiff(VNode<ACTION> a, VNode<ACTION> b, int index) {
        if (a.equals(b)) {
            return Vector.empty();
        }

        if (a.getType() != b.getType()) {
            return Vector.of(new ReplacePatch(index, b));
        }

        Vector<Patch> result = diffAttributes(index, a, b);

        final Array<VNode<ACTION>> aChildren = a.getChildren();
        final Array<VNode<ACTION>> bChildren = b.getChildren();
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

    private static <ACTION> Vector<Patch> diffAttributes(int index, VNode<ACTION> a, VNode<ACTION> b) {
        final Map<VPropertyType, VProperty<?, ACTION>> removedProperties = a.getProperties().filter(propertyA -> !b.getProperties().containsKey(propertyA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<VPropertyType, VProperty<?, ACTION>> updatedProperties = b.getProperties().filter(propertyB -> !Option.of(propertyB._2).equals(a.getProperties().get(propertyB._1)));
        final Map<VPropertyType, VProperty<?, ACTION>> diffProperties = removedProperties.merge(updatedProperties);

        final Map<VEventType, VEventHandlerElement<?, ACTION>> removedEventHandlers = a.getEventHandlers().filter(handlerA -> !b.getEventHandlers().containsKey(handlerA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<VEventType, VEventHandlerElement<?, ACTION>> updatedEventHandlers = b.getEventHandlers().filter(handlerB -> !Option.of(handlerB._2).equals(a.getEventHandlers().get(handlerB._1)));
        final Map<VEventType, VEventHandlerElement<?, ACTION>> diffEventHandlers = removedEventHandlers.merge(updatedEventHandlers);

        return diffProperties.isEmpty() && diffEventHandlers.isEmpty()? Vector.empty()
                : Vector.of(new AttributesPatch<>(index, diffProperties, diffEventHandlers));
    }


}
