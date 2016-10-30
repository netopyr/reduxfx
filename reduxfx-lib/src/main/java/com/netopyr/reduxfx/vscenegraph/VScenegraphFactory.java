package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.patcher.ReduxFXListView;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javaslang.collection.Array;
import javaslang.collection.Seq;
import javaslang.control.Option;

import java.util.function.Function;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.ACTION;
import static com.netopyr.reduxfx.vscenegraph.event.VEventType.MOUSE_CLICKED;

public class VScenegraphFactory {

    private VScenegraphFactory() {}



    @SafeVarargs
    public static <ACTION> VNode<ACTION> node(Class<? extends Node> nodeClass, VElement<ACTION>... elements) {
        return new VNode<>(nodeClass, elements);
    }


    public static <TYPE, ACTION> VProperty<TYPE, ACTION> property(String name, TYPE value, VChangeListener<? super TYPE, ACTION> listener) {
        return new VProperty<>(name, value, Option.of(listener), Option.none());
    }
    public static <TYPE, ACTION> VProperty<TYPE, ACTION> property(String name, VChangeListener<? super TYPE, ACTION> listener) {
        return new VProperty<>(name, Option.of(listener), Option.none());
    }
    public static <T, ACTION> VProperty<T, ACTION> property(String name, T value) {
        return new VProperty<>(name, value, Option.none(), Option.none());
    }


    public static <EVENT extends Event, ACTION> VEventHandlerElement<EVENT, ACTION> onEvent(VEventType type, VEventHandler<EVENT, ACTION> eventHandler) {
        return new VEventHandlerElement<>(type, eventHandler);
    }



    @SafeVarargs
    public static <ACTION> VNode<ACTION> StackPane(VElement<ACTION>... elements) {
        return node(StackPane.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> VBox(VElement<ACTION>... elements) {
        return node(VBox.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> HBox(VElement<ACTION>... elements) {
        return node(HBox.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> AnchorPane(VElement<ACTION>... elements) {
        return node(AnchorPane.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> Button(VElement<ACTION>... elements) {
        return node(Button.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> TextField(VElement<ACTION>... elements) {
        return node(TextField.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> ListView(VElement<ACTION>... elements) {
        return node(ReduxFXListView.class, elements);
    }
    @SafeVarargs
    public static <ACTION> VNode<ACTION> ListView(Class<?> clazz, VElement<ACTION>... elements) {
        return node(ReduxFXListView.class, elements);
    }
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> ListView(VElement<ACTION>... elements) {
//        return node(ListView.class, elements);
//    }
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> ListView(Class<?> clazz, VElement<ACTION>... elements) {
//        return node(ListView.class, elements);
//    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> TableView(Class<?> clazz, VElement<ACTION>... elements) {
        return node(TableView.class, elements);
    }

//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> TableColumn(VElement<ACTION>... elements) {
//        return node(VNodeType.TABLE_COLUMN, elements);
//    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> Label(VElement<ACTION>... elements) {
        return node(Label.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> ToggleButton(VElement<ACTION>... elements) {
        return node(ToggleButton.class, elements);
    }

    @SafeVarargs
    public static <ACTION> VNode<ACTION> CheckBox(VElement<ACTION>... elements) {
        return node(CheckBox.class, elements);
    }


    public static <ACTION> VProperty<String, ACTION> id(String value) {
        return property("id", value);
    }
    public static <ACTION> VProperty<Insets, ACTION> padding(double top, double rightLeft, double bottom) {
        return property("padding", new Insets(top, rightLeft, bottom, rightLeft));
    }
    public static <ACTION> VProperty<Insets, ACTION> padding(double topBottom, double rightLeft) {
        return property("padding", new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }
    public static <ACTION> VProperty<Insets, ACTION> padding(double value) {
        return property("padding", new Insets(value, value, value, value));
    }

    public static <ACTION> VProperty<Double, ACTION> spacing(double value) {
        return property("spacing", value);
    }

    public static <ACTION> VProperty<String, ACTION> text(String value, VChangeListener<? super String, ACTION> listener) {
        return property("text", value, listener);
    }

    public static <ACTION> VProperty<String, ACTION> text(VChangeListener<? super String, ACTION> listener) {
        return property("text", listener);
    }

    public static <ACTION> VProperty<String, ACTION> text(String value) {
        return text(value, null);
    }

    public static <ACTION> VProperty<Boolean, ACTION> selected(boolean value) {
        return property("selected", value);
    }

    public static <ACTION> VProperty<Boolean, ACTION> disable(boolean value) {
        return property("disable", value);
    }

    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(Seq<?> value) {
        return property("data", value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
    }
//    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(Seq<?> value) {
//        return property("items", value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
//    }
//    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(List<?> value) {
//        return property("items", value == null? FXCollections.emptyObservableList() : value instanceof ObservableList? (ObservableList<?>) value : FXCollections.observableList(value));
//    }
//    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(Object... value) {
//        return property("items", value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
//    }

    public static <ACTION> VProperty<String, ACTION> toggleGroup(String value) {
        return property("toggleGroup", value);
    }

    @SafeVarargs
    public static <ACTION> VProperty<Array<VNode<ACTION>>, ACTION> columns(VNode<ACTION>... value) {
        return property("columns", value != null? Array.of(value) : Array.empty());
    }

    public static <ACTION> VProperty<Function<Object, Object>, ACTION> cellFactory(Function<Object, Object> value) {
        return property("mapping", value);
    }
//    public static <ACTION> VProperty<Function<Object, Object>, ACTION> cellFactory(Function<Object, Object> value) {
//        return property("cell", value);
//    }

    public static <ACTION> VProperty<Pos, ACTION> alignment(Pos value) {
        return property("alignment", value);
    }

    public static <ACTION> VProperty<ObservableList<String>, ACTION> styleClass(String... value) {
        return property("styleClass", value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }

    public static <ACTION> VProperty<Boolean, ACTION> mnemonicParsing(boolean value) {
        return property("mnemonicParsing", value);
    }

    public static <ACTION> VProperty<String, ACTION> promptText(String value) {
        return property("promptText", value);
    }

    public static <ACTION> VProperty<ObservableList<String>, ACTION> stylesheets(String... value) {
        return property("stylesheets", value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }

    public static <ACTION> VProperty<Priority, ACTION> hgrow(Priority value) {
        return property("hgrow", value);
    }

    public static <ACTION> VProperty<Double, ACTION> minHeight(double value) {
        return property("minHeight", value);
    }
    public static <ACTION> VProperty<Double, ACTION> maxHeight(double value) {
        return property("maxHeight", value);
    }
    public static <ACTION> VProperty<Double, ACTION> minWidth(double value) {
        return property("minWidth", value);
    }
    public static <ACTION> VProperty<Double, ACTION> maxWidth(double value) {
        return property("maxWidth", value);
    }

    public static <ACTION> VProperty<Double, ACTION> topAnchor(double value) {
        return property("topAnchor", value);
    }
    public static <ACTION> VProperty<Double, ACTION> rightAnchor(double value) {
        return property("rightAnchor", value);
    }
    public static <ACTION> VProperty<Double, ACTION> bottomAnchor(double value) {
        return property("bottomAnchor", value);
    }
    public static <ACTION> VProperty<Double, ACTION> leftAnchor(double value) {
        return property("leftAnchor", value);
    }

    public static <ACTION> VProperty<VNode<ACTION>, ACTION> graphic(VNode<ACTION> value) {
        return property("graphic", value);
    }

    public static <ACTION> VProperty<Boolean, ACTION> hover(VChangeListener<? super Boolean, ACTION> listener) {
        return property("hover", listener);
    }

    public static <ACTION> VProperty<Boolean, ACTION> visible(boolean value) {
        return property("visible", value);
    }

    public static <ACTION> VProperty<Boolean, ACTION> focused(boolean value, VChangeListener<? super Boolean, ACTION> listener) {
        return property("focused", value, listener);
    }




    public static <ACTION> VEventHandlerElement<ActionEvent, ACTION> onAction(VEventHandler<ActionEvent, ACTION> eventHandler) {
        return onEvent(ACTION, eventHandler);
    }

    public static <ACTION> VEventHandlerElement<MouseEvent, ACTION> onMouseClicked(VEventHandler<MouseEvent, ACTION> eventHandler) {
        return onEvent(MOUSE_CLICKED, eventHandler);
    }
}
