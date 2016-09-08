package com.netopyr.reduxfx.vscenegraph.elements;

import javafx.event.Event;
import javafx.event.EventHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VEventHandler<T extends Event> implements VElement {

    private final String name;
    private final EventHandler<T> eventHandler;

    public VEventHandler(String name, EventHandler<T> eventHandler) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.eventHandler = Objects.requireNonNull(eventHandler, "EventHandler must not be null");
    }

    public String getName() {
        return name;
    }

    public EventHandler<T> getEventHandler() {
        return eventHandler;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }
}
