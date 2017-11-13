package com.netopyr.reduxfx.store;

import com.netopyr.reduxfx.updater.Command;
import org.reactivestreams.Publisher;

public interface Driver extends Publisher<Object> {

    void dispatch(Command command);

}
