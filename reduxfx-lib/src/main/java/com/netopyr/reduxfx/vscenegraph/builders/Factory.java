package com.netopyr.reduxfx.vscenegraph.builders;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.event.Event;
import javafx.scene.Node;
import javaslang.Function3;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Tuple3;
import javaslang.collection.Array;
import javaslang.control.Option;

import java.util.concurrent.TimeUnit;

public class Factory {

    private static final long EXPIRATION_SECONDS = 30;
    private static final long MAX_SIZE = 10000;

    private static final Cache<Tuple3<Class<? extends Node>, Array<VProperty<?>>, Array<VEventHandlerElement<?>>>, VNode> nodeCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();

    private static final Cache<Tuple2<String, Object>, VProperty> propertyWithValueCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();

    private static final Cache<String, VProperty> propertyWithoutValueCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();


    private Factory() {
    }


    public static VNode node(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers,
                             Function3<Class<? extends Node>, Array<VProperty<?>>, Array<VEventHandlerElement<?>>, VNode> creator) {
        return nodeCache.get(
                Tuple.of(nodeClass, properties, eventHandlers),
                tuple -> creator.apply(tuple._1, tuple._2, tuple._3)
        );
    }


    public static <TYPE> VProperty<TYPE> property(String name, TYPE value, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return new VProperty<>(name, value, Option.of(changeListener), Option.of(invalidationListener));
    }

    public static <TYPE> VProperty<TYPE> property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return new VProperty<>(name, value, Option.of(changeListener), Option.none());
    }

    public static <TYPE> VProperty<TYPE> property(String name, TYPE value, VInvalidationListener invalidationListener) {
        return new VProperty<>(name, value, Option.none(), Option.of(invalidationListener));
    }

    @SuppressWarnings("unchecked")
    public static <TYPE> VProperty<TYPE> property(String name, TYPE value) {
        return propertyWithValueCache.get(
                Tuple.of(name, value),
                tuple -> new VProperty<>(name, value, Option.none(), Option.none())
        );
    }

    public static <TYPE> VProperty<TYPE> property(String name, VChangeListener<? super TYPE> changeListener, VInvalidationListener invalidationListener) {
        return new VProperty<>(name, Option.of(changeListener), Option.of(invalidationListener));
    }

    public static <TYPE> VProperty<TYPE> property(String name, VChangeListener<? super TYPE> changeListener) {
        return new VProperty<>(name, Option.of(changeListener), Option.none());
    }

    public static <TYPE> VProperty<TYPE> property(String name, VInvalidationListener invalidationListener) {
        return new VProperty<>(name, Option.none(), Option.of(invalidationListener));
    }

    @SuppressWarnings("unchecked")
    public static <TYPE> VProperty<TYPE> property(String name) {
        return propertyWithoutValueCache.get(
                name,
                key -> new VProperty(name, Option.none(), Option.none())
        );
    }


    public static <EVENT extends Event> VEventHandlerElement<EVENT> onEvent(VEventType type, VEventHandler<EVENT> eventHandler) {
        return new VEventHandlerElement<>(type, eventHandler);
    }
}
