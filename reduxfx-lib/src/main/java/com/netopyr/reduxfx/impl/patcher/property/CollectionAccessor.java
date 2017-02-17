package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;

import java.lang.invoke.MethodHandle;
import java.util.Collection;

public class CollectionAccessor implements Accessor {

    private final MethodHandle getter;

    CollectionAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            final Collection collection;
            try {
                collection = (Collection) getter.invoke(node);
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to read property " + name + " from Node-class " + node.getClass(), throwable);
            }
            collection.clear();
            final Object value = vProperty.getValue();
            if (value instanceof Collection) {
                collection.addAll((Collection) vProperty.getValue());
            }
        }
    }
}
