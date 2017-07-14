package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import io.vavr.control.Option;

import java.util.function.Consumer;

public interface NodeAccessor {

    void set(Consumer<Object> dispatcher, Object parent, String name, Option<VNode> vNode);

}
