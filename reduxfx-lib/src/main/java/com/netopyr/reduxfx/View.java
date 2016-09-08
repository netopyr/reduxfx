package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import rx.Observer;

public interface View<STATE, ACTION> {

    VNode view(STATE state, Observer<ACTION> actions);

}
