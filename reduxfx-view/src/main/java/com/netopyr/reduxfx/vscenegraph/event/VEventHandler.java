package com.netopyr.reduxfx.vscenegraph.event;

import javafx.event.Event;

@FunctionalInterface
public interface VEventHandler<E extends Event> {

    Object onChange(E event);

}
