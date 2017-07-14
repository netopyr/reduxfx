package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import io.vavr.collection.Array;

import java.util.function.Consumer;

public interface NodeListAccessor {

    void set(Consumer<Object> dispatcher, Object parent, String name, Array<VNode> vNodeList);
}
