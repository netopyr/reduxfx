package com.netopyr.reduxfx.vscenegraph.event;

import javafx.event.Event;

@FunctionalInterface
public interface VEventHandler<EVENT extends Event> {

    Object onChange(EVENT event);

}
