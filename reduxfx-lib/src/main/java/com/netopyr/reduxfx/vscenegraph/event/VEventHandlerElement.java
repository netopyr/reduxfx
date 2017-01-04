package com.netopyr.reduxfx.vscenegraph.event;

import javafx.event.Event;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VEventHandlerElement<EVENT extends Event> {

    private final VEventType type;
    private final VEventHandler<EVENT> eventHandler;

    public VEventHandlerElement(VEventType type, VEventHandler<EVENT> eventHandler) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.eventHandler = Objects.requireNonNull(eventHandler, "EventHandler must not be null");
    }

    public VEventType getType() {
        return type;
    }

    public VEventHandler<EVENT> getEventHandler() {
        return eventHandler;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .toString();
    }
}
