package com.netopyr.reduxfx.patcher;

import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import com.netopyr.reduxfx.patcher.patches.AttributesPatch;
import com.netopyr.reduxfx.patcher.patches.InsertPatch;
import com.netopyr.reduxfx.patcher.patches.Patch;
import com.netopyr.reduxfx.patcher.patches.RemovePatch;
import com.netopyr.reduxfx.patcher.patches.ReplacePatch;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javaslang.Tuple;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.collection.Vector;
import javaslang.control.Option;

public class Differ {

    public static Vector<Patch> diff(VNode a, VNode b) {
        return doDiff(a, b, 0);
    }

    private static Vector<Patch> doDiff(VNode a, VNode b, int index) {
        if (a.equals(b)) {
            return Vector.empty();
        }

        if (a.getType() != b.getType()) {
            return Vector.of(new ReplacePatch(index, b));
        }

        Vector<Patch> result = diffAttributes(index, a, b);

        final Seq<VNode> aChildren = a.getChildren();
        final Seq<VNode> bChildren = b.getChildren();
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
        final Map<String, Object> removedProperties = a.getProperties().filter(propertyA -> !b.getProperties().containsKey(propertyA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<String, Object> updatedProperties = b.getProperties().filter(propertyB -> !Option.of(propertyB._2).equals(a.getProperties().get(propertyB._1)));
        final Map<String, Object> diffProperties = removedProperties.merge(updatedProperties);

        final Map<String, EventHandler<?>> removedEventHandlers = a.getEventHandlers().filter(handlerA -> !b.getEventHandlers().containsKey(handlerA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<String, EventHandler<?>> updatedEventHandlers = b.getEventHandlers().filter(handlerB -> !Option.of(handlerB._2).equals(a.getEventHandlers().get(handlerB._1)));
        final Map<String, EventHandler<?>> diffEventHandlers = removedEventHandlers.merge(updatedEventHandlers);

        final Map<String, ChangeListener<?>> removedChangeListeners = a.getChangeListeners().filter(handlerA -> !b.getChangeListeners().containsKey(handlerA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<String, ChangeListener<?>> updatedChangeListeners = b.getChangeListeners().filter(handlerB -> !Option.of(handlerB._2).equals(a.getChangeListeners().get(handlerB._1)));
        final Map<String, ChangeListener<?>> diffChangeListeners = removedChangeListeners.merge(updatedChangeListeners);

        final Map<String, InvalidationListener> removedInvalidationListeners = a.getInvalidationListeners().filter(handlerA -> !b.getInvalidationListeners().containsKey(handlerA._1)).map((key, value) -> Tuple.of(key, null));
        final Map<String, InvalidationListener> updatedInvalidationListeners = b.getInvalidationListeners().filter(handlerB -> !Option.of(handlerB._2).equals(a.getInvalidationListeners().get(handlerB._1)));
        final Map<String, InvalidationListener> diffInvalidationListeners = removedInvalidationListeners.merge(updatedInvalidationListeners);

        return diffProperties.isEmpty() && diffEventHandlers.isEmpty() && diffChangeListeners.isEmpty() && diffInvalidationListeners.isEmpty() ? Vector.empty()
                : Vector.of(new AttributesPatch(index, diffProperties, diffEventHandlers, diffChangeListeners, diffInvalidationListeners));
    }


}
