package com.netopyr.reduxfx;

@FunctionalInterface
public interface Reducer<STATE, ACTION> {

    STATE reduce(STATE state, ACTION action);

}
