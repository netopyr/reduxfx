package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;

@FunctionalInterface
public interface Accessor {

    void set(Object node, String name, VProperty vProperty);

}
