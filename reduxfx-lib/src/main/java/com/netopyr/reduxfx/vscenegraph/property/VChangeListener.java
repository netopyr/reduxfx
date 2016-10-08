package com.netopyr.reduxfx.vscenegraph.property;

public interface VChangeListener<TYPE, ACTION> {

    ACTION onChange(TYPE oldValue, TYPE newValue);

}
