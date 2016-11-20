package com.netopyr.reduxfx.vscenegraph.factories;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import javafx.event.Event;
import javaslang.Tuple;
import javaslang.Tuple2;

import java.util.concurrent.TimeUnit;

import static com.netopyr.reduxfx.vscenegraph.factories.CacheConfig.EXPIRATION_SECONDS;
import static com.netopyr.reduxfx.vscenegraph.factories.CacheConfig.MAX_SIZE;

public class VEventHandlerFactory {


    private final Cache<Tuple2<VEventType, VEventHandler>, VEventHandlerElement> eventHandlerCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();

    @SuppressWarnings("unchecked")
    public <EVENT extends Event, ACTION> VEventHandlerElement<EVENT, ACTION> create(VEventType type, VEventHandler<EVENT, ACTION> eventHandler) {
        return eventHandlerCache.get(
                Tuple.of(type, eventHandler),
                tuple -> new VEventHandlerElement<>(type, eventHandler)
        );
    }
}
