package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.VElement;
import com.netopyr.reduxfx.vscenegraph.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VNodeType;
import com.netopyr.reduxfx.vscenegraph.VProperty;
import com.netopyr.reduxfx.vscenegraph.VReference;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.function.Consumer;

public class VScenegraphFactory {

    private VScenegraphFactory() {}



    private static VNode node(VNodeType type, VElement... elements) {
        return new VNode(type, elements);
    }

    public static VReference ref(Consumer<Object> ref) {
        return new VReference(ref);
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



    static VNode Root(VElement... elements) {
        return node(VNodeType.ROOT, elements);
    }

    public static VNode StackPane(VElement... elements) {
        return node(VNodeType.STACK_PANE, elements);
    }

    public static VNode VBox(VElement... elements) {
        return node(VNodeType.V_BOX, elements);
    }

    public static VNode TextField(VElement... elements) {
        return node(VNodeType.TEXT_FIELD, elements);
    }

    public static VNode ListView(VElement... elements) {
        return node(VNodeType.LIST_VIEW, elements);
    }
}
