package com.netopyr.reduxfx.component.command;

import com.netopyr.reduxfx.updater.Command;
import javafx.event.Event;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class FireEventCommand<EVENT extends Event> implements Command {

    private final String eventName;
    private final EVENT event;

    public FireEventCommand(String eventName, EVENT event) {
        this.eventName = eventName;
        this.event = event;
    }

    public String getEventName() {
        return eventName;
    }

    public EVENT getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("eventName", eventName)
                .append("event", event)
                .toString();
    }
}
