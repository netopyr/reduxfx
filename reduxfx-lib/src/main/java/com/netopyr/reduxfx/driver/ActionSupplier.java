package com.netopyr.reduxfx.driver;

import io.reactivex.Observable;

public interface ActionSupplier {

    Observable<?> getActionObservable();

}
