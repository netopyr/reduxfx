package com.netopyr.reduxfx;

import com.netopyr.reduxfx.updater.Command;
import io.reactivex.Observable;
import io.reactivex.Observer;

public interface Driver<ACTION> {

    Observer<Command> getCommandObserver();

    Observable<ACTION> getActionObservable();

}
