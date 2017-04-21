package com.netopyr.reduxfx.vscenegraph.property;

@FunctionalInterface
public interface VChangeListener<TYPE> {

    Object onChange(TYPE oldValue, TYPE newValue);

}
