package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.VElement;
import com.netopyr.reduxfx.vscenegraph.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.VEventType;
import com.netopyr.reduxfx.vscenegraph.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VNodeType;
import com.netopyr.reduxfx.vscenegraph.VProperty;
import com.netopyr.reduxfx.vscenegraph.VPropertyType;
import com.netopyr.reduxfx.vscenegraph.VReference;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.util.function.Consumer;

import static com.netopyr.reduxfx.vscenegraph.VEventType.ACTION;

public class VScenegraphFactory {

    private VScenegraphFactory() {}



    private static VNode node(VNodeType type, VElement... elements) {
        return new VNode(type, elements);
    }

    public static VReference ref(Consumer<? super Node> ref) {
        return new VReference(ref);
    }

    private static VProperty property(VPropertyType type, Object value) {
        return new VProperty(type, value);
    }

    private static <T extends Event> VEventHandler<T> onEvent(VEventType type, EventHandler<T> eventHandler) {
        return new VEventHandler<>(type, eventHandler);
    }

    public static <T> VChangeListener<T> onChange(VPropertyType type, ChangeListener<T> listener) {
        return new VChangeListener<>(type, listener);
    }

    public static VInvalidationListener onInvalidation(VPropertyType type, InvalidationListener listener) {
        return new VInvalidationListener(type, listener);
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



    public static VProperty items(Object value) {
        return property(VPropertyType.ITEMS, value);
    }



    public static <T extends Event> VEventHandler<T> onAction(EventHandler<T> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }
}
