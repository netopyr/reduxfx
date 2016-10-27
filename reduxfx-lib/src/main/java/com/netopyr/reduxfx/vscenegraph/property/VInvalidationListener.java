package com.netopyr.reduxfx.vscenegraph.property;

@FunctionalInterface
public interface VInvalidationListener<ACTION> {

    ACTION onInvalidation();

}
