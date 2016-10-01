package com.netopyr.reduxfx.vscenegraph;

import javafx.event.Event;

public interface VEventHandler<EVENT extends Event, ACTION> {

    ACTION onChange(EVENT event);

}
