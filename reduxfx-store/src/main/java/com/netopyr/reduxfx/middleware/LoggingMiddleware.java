package com.netopyr.reduxfx.middleware;

import com.netopyr.reduxfx.updater.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;

public class LoggingMiddleware<S> implements Middleware<S> {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingMiddleware.class);

    @Override
    public BiFunction<S, Object, Update<S>> apply(BiFunction<S, Object, Update<S>> next) {
        return (oldState, action) -> {
            final Update<S> newState = next.apply(oldState, action);

            LOG.debug("prev state: {}", oldState);
            LOG.debug("action:     {}", action);
            LOG.debug("next state: {}", newState);

            return newState;
        };
    }
}
