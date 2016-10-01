package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.VNode;

public interface View<STATE, ACTION> {

    VNode<ACTION> view(STATE state);

}
