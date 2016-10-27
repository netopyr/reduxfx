package com.netopyr.reduxfx.vscenegraph.property;

@FunctionalInterface
public interface VChangeListener<TYPE, ACTION> {

    ACTION onChange(TYPE oldValue, TYPE newValue);

}
