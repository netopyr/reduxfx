package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.function.Consumer;

public interface View<STATE, ACTION> {

    VNode view(STATE state, Consumer<ACTION> dispatcher);

}
