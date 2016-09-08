package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.elements.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.elements.VElement;
import com.netopyr.reduxfx.vscenegraph.elements.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.elements.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.elements.VNode;
import com.netopyr.reduxfx.vscenegraph.elements.VNodeType;
import com.netopyr.reduxfx.vscenegraph.elements.VProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;

public class VScenegraph {


    public static VNode node(VNodeType type, VElement... elements) {
        return new VNode(type, elements);
    }

    public static VProperty property(String name, Object value) {
        return new VProperty(name, value);
    }

    public static <T extends Event> VEventHandler<T> onEvent(String name, EventHandler<T> eventHandler) {
        return new VEventHandler<>(name, eventHandler);
    }

    public static <T> VChangeListener<T> onChange(String name, ChangeListener<T> listener) {
        return new VChangeListener<>(name, listener);
    }

    public static VInvalidationListener onInvalidation(String name, InvalidationListener listener) {
        return new VInvalidationListener(name, listener);
    }
}
