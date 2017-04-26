package com.netopyr.reduxfx.driver;

import com.netopyr.reduxfx.updater.Command;
import org.reactivestreams.Subscriber;

public interface CommandConsumer {

    Subscriber<Command> getCommandSubscriber();

}
