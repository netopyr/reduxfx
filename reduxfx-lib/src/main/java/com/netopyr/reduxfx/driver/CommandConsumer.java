package com.netopyr.reduxfx.driver;

import com.netopyr.reduxfx.updater.Command;
import io.reactivex.Observer;

public interface CommandConsumer {

    Observer<Command> getCommandObserver();

}
