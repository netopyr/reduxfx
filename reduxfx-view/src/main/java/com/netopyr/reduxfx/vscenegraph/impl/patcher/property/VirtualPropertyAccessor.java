package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;

import java.util.function.Consumer;

public class VirtualPropertyAccessor implements Accessor {

    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            NodeUtilities.getProperties(node).put(name, vProperty.getValue());
        } else {
            NodeUtilities.getProperties(node).remove(name);
        }
    }

}
