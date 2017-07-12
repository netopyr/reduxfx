package com.netopyr.reduxfx.driver;

import org.reactivestreams.Publisher;

public interface ActionSupplier {

    Publisher<Object> getActionPublisher();

}
