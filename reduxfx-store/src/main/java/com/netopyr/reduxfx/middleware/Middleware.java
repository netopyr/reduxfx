package com.netopyr.reduxfx.middleware;

import com.netopyr.reduxfx.updater.Update;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public interface Middleware<S> extends UnaryOperator<BiFunction<S, Object, Update<S>>> {}
