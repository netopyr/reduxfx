package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javaslang.collection.Array;
import javaslang.collection.Seq;
import javaslang.control.Option;

import java.util.List;
import java.util.function.Function;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.ACTION;

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
    public static <ACTION> VNode<ACTION> Root(VElement<ACTION>... elements) {
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
    public static <ACTION> VNode<ACTION> TableView(Class<?> clazz, VElement<ACTION>... elements) {
        return node(VNodeType.TABLE_VIEW, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> TableColumn(VElement<ACTION>... elements) {
        return node(VNodeType.TABLE_COLUMN, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> Label(VElement<ACTION>... elements) {
        return node(VNodeType.LABEL, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> ToggleButton(VElement<ACTION>... elements) {
        return node(VNodeType.TOGGLE_BUTTON, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> CheckBox(VElement<ACTION>... elements) {
        return node(VNodeType.CHECK_BOX, elements);
    }


    public static <ACTION> VProperty<String, ACTION> id(String value) {
        return property(VPropertyType.ID, value);
    }
    public static <ACTION> VProperty<Insets, ACTION> padding(double topBottom, double rightLeft) {
        return property(VPropertyType.PADDING, new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }
    public static <ACTION> VProperty<Insets, ACTION> padding(double top, double rightLeft, double bottom) {
        return property(VPropertyType.PADDING, new Insets(top, rightLeft, bottom, rightLeft));
    }

    public static <ACTION> VProperty<Double, ACTION> spacing(double value) {
        return property(VPropertyType.SPACING, value);
    }

    public static <ACTION> VProperty<String, ACTION> text(String value, VChangeListener<? super String, ACTION> listener) {
        return property(VPropertyType.TEXT, value, listener);
    }

    public static <ACTION> VProperty<String, ACTION> text(String value) {
        return text(value, null);
    }

    public static <ACTION> VProperty<Boolean, ACTION> selected(boolean value) {
        return property(VPropertyType.SELECTED, value);
    }

    public static <ACTION> VProperty<Boolean, ACTION> disable(boolean value) {
        return property(VPropertyType.DISABLE, value);
    }

    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(Seq<?> value) {
        return property(VPropertyType.ITEMS, value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
    }
    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(List<?> value) {
        return property(VPropertyType.ITEMS, value == null? FXCollections.emptyObservableList() : value instanceof ObservableList? (ObservableList<?>) value : FXCollections.observableList(value));
    }
    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(Object... value) {
        return property(VPropertyType.ITEMS, value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }

    public static <ACTION> VProperty<String, ACTION> toggleGroup(String value) {
        return property(VPropertyType.TOGGLE_GROUP, value);
    }

    @SafeVarargs
    public static <ACTION> VProperty<Array<VNode<ACTION>>, ACTION> columns(VNode<ACTION>... value) {
        return property(VPropertyType.COLUMNS, value != null? Array.of(value) : Array.empty());
    }

    public static <ACTION> VProperty<Function<?, ?>, ACTION> cellValueFactoy(Function<?, ?> value) {
        return property(VPropertyType.TABLE_CELL, value);
    }

    public static <ACTION> VProperty<Pos, ACTION> alignment(Pos value) {
        return property(VPropertyType.ALIGNMENT, value);
    }

    public static <ACTION> VProperty<ObservableList<String>, ACTION> styleClass(String... value) {
        return property(VPropertyType.STYLE_CLASS, value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }

    public static <ACTION> VProperty<Boolean, ACTION> mnemonicParsing(boolean value) {
        return property(VPropertyType.MNEMONIC_PARSING, value);
    }

    public static <ACTION> VProperty<String, ACTION> promptText(String value) {
        return property(VPropertyType.PROMPT_TEXT, value);
    }

    public static <ACTION> VProperty<ObservableList<String>, ACTION> stylesheets(String... value) {
        return property(VPropertyType.STYLESHEETS, value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }

    public static <ACTION> VProperty<Priority, ACTION> hgrow(Priority value) {
        return property(VPropertyType.H_GROW, value);
    }



    public static <ACTION> VEventHandlerElement<ActionEvent, ACTION> onAction(VEventHandler<ActionEvent, ACTION> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }
}
