//package com.netopyr.reduxfx.vscenegraph.factories;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.netopyr.reduxfx.vscenegraph.VElement;
//import com.netopyr.reduxfx.vscenegraph.VNode;
//import javafx.scene.Node;
//import javaslang.Tuple;
//import javaslang.Tuple2;
//import javaslang.collection.Array;
//
//import java.util.concurrent.TimeUnit;
//
//import static com.netopyr.reduxfx.vscenegraph.factories.CacheConfig.EXPIRATION_SECONDS;
//import static com.netopyr.reduxfx.vscenegraph.factories.CacheConfig.MAX_SIZE;
//
//public class VNodeFactory {
//
//    private final Cache<Tuple2<Class<? extends Node>, Array>, VNode> nodeCache =
//            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
//                    .maximumSize(MAX_SIZE)
//                    .build();
//
//    @SafeVarargs
//    @SuppressWarnings("unchecked")
//    public final <ACTION> VNode<ACTION> create(Class<? extends Node> nodeClass, VElement<ACTION>... elements) {
//
//        final Array<VElement<ACTION>> elementArray = elements != null? Array.of(elements) : Array.empty();
//        return nodeCache.get(
//                Tuple.of(nodeClass, elementArray),
//                tuple -> new VNode<>(nodeClass, elementArray)
//        );
//    }
//
//}
