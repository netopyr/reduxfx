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
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.Tuple3;
import javaslang.collection.Array;
import javaslang.control.Option;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Factory {

    private static final long EXPIRATION_SECONDS = 30;
    private static final long MAX_SIZE = 10000;

    private static final Cache<Tuple3<Object, Array<VProperty<?>>, Array<VEventHandlerElement<?>>>, VNode> nodeCache =
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


    @SuppressWarnings("unchecked")
    public static <BUILDER extends NodeBuilder<BUILDER>> BUILDER node(NodeBuilder<BUILDER> builder, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) nodeCache.get(
                Tuple.of(builder.getTypeKey(), properties, eventHandlers),
                tuple -> builder.create(properties, eventHandlers)
        );
    }

    @SuppressWarnings("unchecked")
    public static <BUILDER extends VNode> BUILDER node(Object typeKey, Supplier<BUILDER> factory) {
        return (BUILDER) nodeCache.get(
                Tuple.of(typeKey, Array.empty(), Array.empty()),
                tuple -> factory.get()
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

    @SuppressWarnings("unchecked")
    public static VProperty property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return new VProperty(name, Option.of(changeListener), Option.of(invalidationListener));
    }

    @SuppressWarnings("unchecked")
    public static VProperty property(String name, VChangeListener<?> changeListener) {
        return new VProperty(name, Option.of(changeListener), Option.none());
    }

    @SuppressWarnings("unchecked")
    public static VProperty property(String name, VInvalidationListener invalidationListener) {
        return new VProperty(name, Option.none(), Option.of(invalidationListener));
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
