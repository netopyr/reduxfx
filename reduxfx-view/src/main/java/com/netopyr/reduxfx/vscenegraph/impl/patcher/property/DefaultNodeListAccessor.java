package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeBuilder;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Array;

import java.lang.invoke.MethodHandle;
import java.util.List;
import java.util.function.Consumer;

public class DefaultNodeListAccessor implements NodeListAccessor {

    private final MethodHandle getter;

    public DefaultNodeListAccessor(MethodHandle getter) {
        this.getter = getter;
    }

    @SuppressWarnings("unchecked")
    @Override
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
            final Array<Tuple2<VNode, Object>> nodeList = vNodeList
                    .map(vNode -> Tuple.of(vNode, NodeBuilder.create(vNode)))
                    .filter(tuple -> tuple._2.isDefined())
                    .map(tuple -> Tuple.of(tuple._1, tuple._2.get()));
            list.addAll(nodeList.map(tuple -> tuple._2).toJavaList());
            nodeList.forEach(tuple -> NodeBuilder.init(dispatcher, tuple._2, tuple._1));
        }
    }
}
