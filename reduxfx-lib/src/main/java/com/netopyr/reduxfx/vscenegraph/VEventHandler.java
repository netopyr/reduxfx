package com.netopyr.reduxfx.vscenegraph;

import javafx.event.Event;
import javafx.event.EventHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VEventHandler<T extends Event> implements VElement {

    private final VEventType type;
    private final EventHandler<T> eventHandler;

    public VEventHandler(VEventType type, EventHandler<T> eventHandler) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.eventHandler = Objects.requireNonNull(eventHandler, "EventHandler must not be null");
    }

    public VEventType getType() {
        return type;
    }

    public EventHandler<T> getEventHandler() {
        return eventHandler;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .toString();
    }
}
