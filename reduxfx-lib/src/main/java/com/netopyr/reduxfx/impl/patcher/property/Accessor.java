package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;

@FunctionalInterface
public interface Accessor {

    void set(Object node, String name, VProperty vProperty);

}
