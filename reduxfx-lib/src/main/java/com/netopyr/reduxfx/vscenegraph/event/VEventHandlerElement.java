package com.netopyr.reduxfx.vscenegraph.event;

import com.netopyr.reduxfx.vscenegraph.VElement;
import javafx.event.Event;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public final class VEventHandlerElement<EVENT extends Event, ACTION> implements VElement<ACTION> {

    private final VEventType type;
    private final VEventHandler<EVENT, ACTION> eventHandler;

    public VEventHandlerElement(VEventType type, VEventHandler<EVENT, ACTION> eventHandler) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.eventHandler = Objects.requireNonNull(eventHandler, "EventHandler must not be null");
    }

    public VEventType getType() {
        return type;
    }

    public VEventHandler<EVENT, ACTION> getEventHandler() {
        return eventHandler;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .toString();
    }
}
