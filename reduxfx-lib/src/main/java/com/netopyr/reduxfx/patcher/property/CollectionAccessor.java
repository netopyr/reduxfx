package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;

import java.lang.invoke.MethodHandle;
import java.util.Collection;

public class CollectionAccessor<TYPE extends Collection> implements Accessor<TYPE> {

    private final MethodHandle getter;

    public CollectionAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void set(Node node, VProperty<TYPE> vProperty) {
        if (vProperty.isValueDefined()) {
            final Collection collection;
            try {
                collection = (Collection) getter.invoke(node);
            } catch (Throwable throwable) {
                throw new IllegalStateException("Unable to read property " + vProperty.getName() + " from Node-class " + node.getClass(), throwable);
            }
            collection.clear();
            collection.addAll(vProperty.getValue());
        }
    }
}
