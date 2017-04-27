package com.netopyr.reduxfx.middleware;

import com.netopyr.reduxfx.updater.Update;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public interface Middleware<STATE> extends UnaryOperator<BiFunction<STATE, Object, Update<STATE>>> {}
