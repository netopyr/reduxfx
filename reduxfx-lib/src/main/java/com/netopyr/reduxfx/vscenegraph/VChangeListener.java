package com.netopyr.reduxfx.vscenegraph;

public interface VChangeListener<TYPE, ACTION> {

    ACTION onChange(TYPE oldValue, TYPE newValue);

}
