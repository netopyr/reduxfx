package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.control.Option;

import java.util.function.Consumer;

public interface NodeAccessor {

    void set(Consumer<Object> dispatcher, Object parent, String name, Option<VNode> vNode);

}
