package com.netopyr.reduxfx;

public interface Reducer<STATE, ACTION> {

    STATE reduce(STATE state, ACTION action);

}
