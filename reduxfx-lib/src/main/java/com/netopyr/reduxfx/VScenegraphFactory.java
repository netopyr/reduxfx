package com.netopyr.reduxfx;

import com.netopyr.reduxfx.vscenegraph.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.VElement;
import com.netopyr.reduxfx.vscenegraph.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.VEventType;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VNodeType;
import com.netopyr.reduxfx.vscenegraph.VProperty;
import com.netopyr.reduxfx.vscenegraph.VPropertyType;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javaslang.collection.Seq;
import javaslang.control.Option;

import static com.netopyr.reduxfx.vscenegraph.VEventType.ACTION;

public class VScenegraphFactory {

    private VScenegraphFactory() {}



    @SafeVarargs
    private static <ACTION> VNode<ACTION> node(VNodeType type, VElement<ACTION>... elements) {
        return new VNode<>(type, elements);
    }

    private static <TYPE, ACTION> VProperty<TYPE, ACTION> property(VPropertyType type, TYPE value, VChangeListener<? super TYPE, ACTION> listener) {
        return new VProperty<>(type, value, Option.of(listener), Option.none());
    }

    private static <T, ACTION> VProperty<T, ACTION> property(VPropertyType type, T value) {
        return new VProperty<>(type, value, Option.none(), Option.none());
    }

    private static <EVENT extends Event, ACTION> VEventHandlerElement<EVENT, ACTION> onEvent(VEventType type, VEventHandler<EVENT, ACTION> eventHandler) {
        return new VEventHandlerElement<>(type, eventHandler);
    }



    @SafeVarargs
    static <ACTION> VNode<ACTION> Root(VElement<ACTION>... elements) {
        return node(VNodeType.ROOT, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> StackPane(VElement<ACTION>... elements) {
        return node(VNodeType.STACK_PANE, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> VBox(VElement<ACTION>... elements) {
        return node(VNodeType.V_BOX, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> HBox(VElement<ACTION>... elements) {
        return node(VNodeType.H_BOX, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> Button(VElement<ACTION>... elements) {
        return node(VNodeType.BUTTON, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> TextField(VElement<ACTION>... elements) {
        return node(VNodeType.TEXT_FIELD, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> ListView(VElement<ACTION>... elements) {
        return node(VNodeType.LIST_VIEW, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> ToggleButton(VElement<ACTION>... elements) {
        return node(VNodeType.TOGGLE_BUTTON, elements);
    }


    public static <ACTION> VProperty<String, ACTION> text(String value, VChangeListener<? super String, ACTION> listener) {
        return property(VPropertyType.TEXT, value, listener);
    }

    public static <ACTION> VProperty<String, ACTION> text(String value) {
        return text(value, null);
    }

    public static <ACTION> VProperty<Boolean, ACTION> disable(boolean value) {
        return property(VPropertyType.DISABLE, value);
    }

    public static <ACTION> VProperty<Seq<String>, ACTION> items(Seq<String> value) {
        return property(VPropertyType.ITEMS, value);
    }

    public static <ACTION> VProperty<String, ACTION> toggleGroup(String value) {
        return property(VPropertyType.TOGGLE_GROUP, value);
    }



    public static <ACTION> VEventHandlerElement<ActionEvent, ACTION> onAction(VEventHandler<ActionEvent, ACTION> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }
}
