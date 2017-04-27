package com.netopyr.reduxfx.middleware;

import com.netopyr.reduxfx.updater.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiFunction;

public class LoggingMiddleware<STATE> implements Middleware<STATE> {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingMiddleware.class);

    @Override
    public BiFunction<STATE, Object, Update<STATE>> apply(BiFunction<STATE, Object, Update<STATE>> next) {
        return (oldState, action) -> {
            final Update<STATE> newState = next.apply(oldState, action);

            LOG.debug("prev state: {}", oldState);
            LOG.debug("action:     {}", action);
            LOG.debug("next state: {}", newState);

            return newState;
        };
    }
}
