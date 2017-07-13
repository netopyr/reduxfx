package com.netopyr.reduxfx.vscenegraph.property;

@FunctionalInterface
public interface VChangeListener<T> {

    Object onChange(T oldValue, T newValue);

}
