package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.VNode;

@FunctionalInterface
public interface View<STATE, ACTION> {

    VNode<ACTION> view(STATE state);

}
