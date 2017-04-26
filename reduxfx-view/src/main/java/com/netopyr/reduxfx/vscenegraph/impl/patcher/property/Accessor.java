package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;

import java.util.function.Consumer;

@FunctionalInterface
public interface Accessor {

    void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty);
}
