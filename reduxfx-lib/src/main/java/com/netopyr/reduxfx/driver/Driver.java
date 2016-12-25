package com.netopyr.reduxfx.driver;

import com.netopyr.reduxfx.updater.Command;
import io.reactivex.Observable;
import io.reactivex.Observer;

public interface Driver<ACTION> {

    Observer<Command> getCommandObserver();

    Observable<ACTION> getActionObservable();

}
