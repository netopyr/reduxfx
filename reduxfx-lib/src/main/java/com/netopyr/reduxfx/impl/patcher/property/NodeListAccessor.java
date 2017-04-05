package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.impl.patcher.NodeBuilder;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.Tuple;
import javaslang.collection.Array;

import java.lang.invoke.MethodHandle;
import java.util.List;
import java.util.function.Consumer;

public class NodeListAccessor {

    private final MethodHandle getter;

    public NodeListAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    public void set(Consumer<Object> dispatcher, Object parent, String name, Array<VNode> vNodeList) {
        final List list;
        try {
            list = (List) getter.invoke(parent);
        } catch (Throwable throwable) {
            throw new IllegalStateException("Unable to read property " + name + " from class " + parent.getClass(), throwable);
        }

        if (vNodeList.isEmpty()) {
            list.clear();
        } else {
            list.clear();
            vNodeList
                    .map(vNode -> Tuple.of(vNode, NodeBuilder.create(vNode)))
                    .filter(tuple -> tuple._2.isDefined())
                    .forEach(tuple -> {
                        list.add(tuple._2.get());
                        NodeBuilder.init(dispatcher, tuple._2.get(), tuple._1);
                    });
        }
    }
}
