package com.netopyr.reduxfx.vscenegraph.factories;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.Tuple;
import javaslang.Tuple4;
import javaslang.control.Option;

import java.util.concurrent.TimeUnit;

import static com.netopyr.reduxfx.vscenegraph.factories.CacheConfig.EXPIRATION_SECONDS;
import static com.netopyr.reduxfx.vscenegraph.factories.CacheConfig.MAX_SIZE;

public class VPropertyFactory {

    private final Cache<Tuple4<String, Object, VChangeListener, VInvalidationListener>, VProperty> propertyCache =
            Caffeine.newBuilder().expireAfterAccess(EXPIRATION_SECONDS, TimeUnit.SECONDS)
                    .maximumSize(MAX_SIZE)
                    .build();

    @SuppressWarnings("unchecked")
    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, TYPE value, VChangeListener<? super TYPE, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
        return propertyCache.get(
                Tuple.of(name, value, changeListener, invalidationListener),
                tuple -> new VProperty<>(name, value, Option.of(changeListener), Option.of(invalidationListener))
        );
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, TYPE value, VChangeListener<? super TYPE, ACTION> changeListener) {
        return create(name, value, changeListener, null);
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, TYPE value, VInvalidationListener<ACTION> invalidationListener) {
        return create(name, value, null, invalidationListener);
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, TYPE value) {
        return create(name, value, null, null);
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, VChangeListener<? super TYPE, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
        return create(name, VProperty.getNoValue(), changeListener, invalidationListener);
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, VChangeListener<? super TYPE, ACTION> changeListener) {
        return create(name, VProperty.getNoValue(), changeListener, null);
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name, VInvalidationListener<ACTION> invalidationListener) {
        return create(name, VProperty.getNoValue(), null, invalidationListener);
    }

    public <TYPE, ACTION> VProperty<TYPE, ACTION> create(String name) {
        return create(name, VProperty.getNoValue(), null, null);
    }


}
