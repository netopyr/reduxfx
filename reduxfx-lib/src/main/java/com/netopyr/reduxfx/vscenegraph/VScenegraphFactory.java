package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.patcher.ReduxFXListView;
import com.netopyr.reduxfx.vscenegraph.builders.*;
import com.netopyr.reduxfx.vscenegraph.builders.ButtonBaseBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ButtonBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.CheckBoxBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ControlBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.LabelBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.LabeledBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ListViewBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ProgressBarBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ProgressIndicatorBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.SliderBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TextFieldBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TextInputControlBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ToggleButtonBuilder;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javaslang.collection.Array;

public class VScenegraphFactory {

    private VScenegraphFactory() {}

    @SuppressWarnings("unchecked")
    public static <CLASS extends NodeBuilder<CLASS>> NodeBuilder<CLASS> customNode(Class<? extends Node> nodeClass) {
        return (NodeBuilder<CLASS>) Factory.node(nodeClass, Array.empty(), Array.empty(), NodeBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends AnchorPaneBuilder<CLASS>> AnchorPaneBuilder<CLASS> AnchorPane() {
        return (AnchorPaneBuilder<CLASS>) Factory.node(AnchorPane.class, Array.empty(), Array.empty(), AnchorPaneBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends BorderPaneBuilder<CLASS>> BorderPaneBuilder<CLASS> BorderPane() {
        return (BorderPaneBuilder<CLASS>) Factory.node(BorderPane.class, Array.empty(), Array.empty(), BorderPaneBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ButtonBaseBuilder<CLASS>> ButtonBaseBuilder<CLASS> ButtonBase() {
        return (ButtonBaseBuilder<CLASS>) Factory.node(ButtonBase.class, Array.empty(), Array.empty(), ButtonBaseBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ButtonBuilder<CLASS>> ButtonBuilder<CLASS> Button(Class<? extends Button> nodeClass) {
        return (ButtonBuilder<CLASS>) Factory.node(nodeClass, Array.empty(), Array.empty(), ButtonBuilder::new);
    }
    public static <CLASS extends ButtonBuilder<CLASS>> ButtonBuilder<CLASS> Button() {
        return Button(Button.class);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends CheckBoxBuilder<CLASS>> CheckBoxBuilder<CLASS> CheckBox() {
        return (CheckBoxBuilder<CLASS>) Factory.node(CheckBox.class, Array.empty(), Array.empty(), CheckBoxBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ControlBuilder<CLASS>> ControlBuilder<CLASS> Control() {
        return (ControlBuilder<CLASS>) Factory.node(Control.class, Array.empty(), Array.empty(), ControlBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends HBoxBuilder<CLASS>> HBoxBuilder<CLASS> HBox() {
        return (HBoxBuilder<CLASS>) Factory.node(HBox.class, Array.empty(), Array.empty(), HBoxBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends LabelBuilder<CLASS>> LabelBuilder<CLASS> Label() {
        return (LabelBuilder<CLASS>) Factory.node(Label.class, Array.empty(), Array.empty(), LabelBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends LabeledBuilder<CLASS>> LabeledBuilder<CLASS> Labeled() {
        return (LabeledBuilder<CLASS>) Factory.node(Labeled.class, Array.empty(), Array.empty(), LabeledBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ListViewBuilder<CLASS>> ListViewBuilder<CLASS> ListView() {
        return (ListViewBuilder<CLASS>) Factory.node(ReduxFXListView.class, Array.empty(), Array.empty(), ListViewBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends NodeBuilder<CLASS>> NodeBuilder<CLASS> Node() {
        return (NodeBuilder<CLASS>) Factory.node(Node.class, Array.empty(), Array.empty(), NodeBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends PaneBuilder<CLASS>> PaneBuilder<CLASS> Pane() {
        return (PaneBuilder<CLASS>) Factory.node(Pane.class, Array.empty(), Array.empty(), PaneBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ParentBuilder<CLASS>> ParentBuilder<CLASS> Parent() {
        return (ParentBuilder<CLASS>) Factory.node(Parent.class, Array.empty(), Array.empty(), ParentBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ProgressBarBuilder<CLASS>> ProgressBarBuilder<CLASS> ProgressBar(Class<? extends ProgressBar> nodeClass) {
        return (ProgressBarBuilder<CLASS>) Factory.node(nodeClass, Array.empty(), Array.empty(), ProgressBarBuilder::new);
    }
    @SuppressWarnings("unchecked")
    public static <CLASS extends ProgressBarBuilder<CLASS>> ProgressBarBuilder<CLASS> ProgressBar() {
        return ProgressBar(ProgressBar.class);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ProgressIndicatorBuilder<CLASS>> ProgressIndicatorBuilder<CLASS> ProgressIndicator() {
        return (ProgressIndicatorBuilder<CLASS>) Factory.node(ProgressIndicator.class, Array.empty(), Array.empty(), ProgressIndicatorBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends RegionBuilder<CLASS>> RegionBuilder<CLASS> Region() {
        return (RegionBuilder<CLASS>) Factory.node(Region.class, Array.empty(), Array.empty(), RegionBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends SliderBuilder<CLASS>> SliderBuilder<CLASS> Slider() {
        return (SliderBuilder<CLASS>) Factory.node(Slider.class, Array.empty(), Array.empty(), SliderBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends StackPaneBuilder<CLASS>> StackPaneBuilder<CLASS> StackPane() {
        return (StackPaneBuilder<CLASS>) Factory.node(StackPane.class, Array.empty(), Array.empty(), StackPaneBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends TextBuilder<CLASS>> TextBuilder<CLASS> Text(Class<? extends Text> nodeClass) {
        return (TextBuilder<CLASS>) Factory.node(nodeClass, Array.empty(), Array.empty(), TextBuilder::new);
    }
    public static <CLASS extends TextBuilder<CLASS>> TextBuilder<CLASS> Text() {
        return Text(Text.class);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends TextFieldBuilder<CLASS>> TextFieldBuilder<CLASS> TextField(Class<? extends TextField> nodeClass) {
        return (TextFieldBuilder<CLASS>) Factory.node(nodeClass, Array.empty(), Array.empty(), TextFieldBuilder::new);
    }
    public static <CLASS extends TextFieldBuilder<CLASS>> TextFieldBuilder<CLASS> TextField() {
        return TextField(TextField.class);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends TextInputControlBuilder<CLASS>> TextInputControlBuilder<CLASS> TextInputControl() {
        return (TextInputControlBuilder<CLASS>) Factory.node(TextInputControl.class, Array.empty(), Array.empty(), TextInputControlBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends ToggleButtonBuilder<CLASS>> ToggleButtonBuilder<CLASS> ToggleButton() {
        return (ToggleButtonBuilder<CLASS>) Factory.node(ToggleButton.class, Array.empty(), Array.empty(), ToggleButtonBuilder::new);
    }

    @SuppressWarnings("unchecked")
    public static <CLASS extends VBoxBuilder<CLASS>> VBoxBuilder<CLASS> VBox() {
        return (VBoxBuilder<CLASS>) Factory.node(VBox.class, Array.empty(), Array.empty(), VBoxBuilder::new);
    }



//    @SafeVarargs
//    @SuppressWarnings("unchecked")
//    public static <ACTION> VNode<ACTION> node(Class<? extends Node> nodeClass, VElement<ACTION>... elements) {
//        return NODE_CACHE.create(nodeClass, elements);
//    }
//
//
//    public static <TYPE, ACTION> VProperty<TYPE, ACTION> property(String name, TYPE value, VChangeListener<? super TYPE, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
//        return PROPERTY_FACTORY.create(name, value, changeListener, invalidationListener);
//    }
//    public static <TYPE, ACTION> VProperty<TYPE, ACTION> property(String name, TYPE value, VChangeListener<? super TYPE, ACTION> listener) {
//        return PROPERTY_FACTORY.create(name, value, listener);
//    }
//
//    public static <TYPE, ACTION> VProperty<TYPE, ACTION> property(String name, VChangeListener<? super TYPE, ACTION> changeListener, VInvalidationListener<ACTION> invalidationListener) {
//        return PROPERTY_FACTORY.create(name, changeListener, invalidationListener);
//    }
//    public static <TYPE, ACTION> VProperty<TYPE, ACTION> property(String name, VChangeListener<? super TYPE, ACTION> listener) {
//        return PROPERTY_FACTORY.create(name, listener);
//    }
//    public static <T, ACTION> VProperty<T, ACTION> property(String name, T value) {
//        return PROPERTY_FACTORY.create(name, value);
//    }
//
//
//    public static <EVENT extends Event, ACTION> VEventHandlerElement<EVENT, ACTION> onEvent(VEventType type, VEventHandler<EVENT, ACTION> eventHandler) {
//        return EVENT_HANDLER_FACTORY.create(type, eventHandler);
//    }
//
//
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> StackPane(VElement<ACTION>... elements) {
//        return node(StackPane.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> VBox(VElement<ACTION>... elements) {
//        return node(VBox.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> HBox(VElement<ACTION>... elements) {
//        return node(HBox.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> AnchorPane(VElement<ACTION>... elements) {
//        return node(AnchorPane.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> Button(VElement<ACTION>... elements) {
//        return node(Button.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> TextField(VElement<ACTION>... elements) {
//        return node(TextField.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> ListView(VElement<ACTION>... elements) {
//        return node(ReduxFXListView.class, elements);
//    }
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> ListView(Class<?> clazz, VElement<ACTION>... elements) {
//        return node(ReduxFXListView.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> TableView(Class<?> clazz, VElement<ACTION>... elements) {
//        return node(TableView.class, elements);
//    }
//
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> Label(VElement<ACTION>... elements) {
//        return node(Label.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> ToggleButton(VElement<ACTION>... elements) {
//        return node(ToggleButton.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> CheckBox(VElement<ACTION>... elements) {
//        return node(CheckBox.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> BorderPane(VElement<ACTION>... elements) {
//        return node(BorderPane.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> GridPane(VElement<ACTION>... elements) {
//        return node(GridPane.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> ProgressBar(VElement<ACTION>... elements) {
//        return node(ProgressBar.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> Slider(VElement<ACTION>... elements) {
//        return node(Slider.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> Region(VElement<ACTION>... elements) {
//        return node(Region.class, elements);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VNode<ACTION> Circle(VElement<ACTION>... elements) {
//        return node(Circle.class, elements);
//    }
//
//
//
//
//    public static <ACTION> VProperty<String, ACTION> id(String value) {
//        return property("id", value);
//    }
//
//    public static <ACTION> VProperty<Insets, ACTION> padding(double top, double rightLeft, double bottom) {
//        return property("padding", new Insets(top, rightLeft, bottom, rightLeft));
//    }
//    public static <ACTION> VProperty<Insets, ACTION> padding(double topBottom, double rightLeft) {
//        return property("padding", new Insets(topBottom, rightLeft, topBottom, rightLeft));
//    }
//    public static <ACTION> VProperty<Insets, ACTION> padding(double value) {
//        return property("padding", new Insets(value, value, value, value));
//    }
//
//    public static <ACTION> VProperty<Insets, ACTION> margin(double top, double rightLeft, double bottom) {
//        return property("margin", new Insets(top, rightLeft, bottom, rightLeft));
//    }
//    public static <ACTION> VProperty<Insets, ACTION> margin(double topBottom, double rightLeft) {
//        return property("margin", new Insets(topBottom, rightLeft, topBottom, rightLeft));
//    }
//    public static <ACTION> VProperty<Insets, ACTION> margin(double value) {
//        return property("margin", new Insets(value, value, value, value));
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> spacing(double value) {
//        return property("spacing", value);
//    }
//
//    public static <ACTION> VProperty<String, ACTION> text(String value, VChangeListener<? super String, ACTION> listener) {
//        return property("text", value, listener);
//    }
//
//    public static <ACTION> VProperty<String, ACTION> text(VChangeListener<? super String, ACTION> listener) {
//        return property("text", listener);
//    }
//
//    public static <ACTION> VProperty<String, ACTION> text(String value) {
//        return text(value, null);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> selected(boolean value) {
//        return property("selected", value);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> disable(boolean value) {
//        return property("disable", value);
//    }
//
//    public static <ACTION> VProperty<ObservableList<?>, ACTION> items(Seq<?> value) {
//        return property("data", value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
//    }
//
//    public static <ACTION> VProperty<String, ACTION> toggleGroup(String value) {
//        return property("toggleGroup", value);
//    }
//
//    @SafeVarargs
//    public static <ACTION> VProperty<Array<VNode<ACTION>>, ACTION> columns(VNode<ACTION>... value) {
//        return property("columns", value != null? Array.of(value) : Array.empty());
//    }
//
//    public static <ACTION> VProperty<Function<Object, Object>, ACTION> cellFactory(Function<Object, Object> value) {
//        return property("mapping", value);
//    }
//
//    public static <ACTION> VProperty<Pos, ACTION> alignment(Pos value) {
//        return property("alignment", value);
//    }
//
//    public static <ACTION> VProperty<ObservableList<String>, ACTION> styleClass(String... value) {
//        return property("styleClass", value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
//    }
//
//    public static <ACTION> VProperty<String, ACTION> style(String value) {
//        return property("style", value);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> mnemonicParsing(boolean value) {
//        return property("mnemonicParsing", value);
//    }
//
//    public static <ACTION> VProperty<String, ACTION> promptText(String value) {
//        return property("promptText", value);
//    }
//
//    public static <ACTION> VProperty<ObservableList<String>, ACTION> stylesheets(String... value) {
//        return property("stylesheets", value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
//    }
//
//    public static <ACTION> VProperty<Priority, ACTION> hgrow(Priority value) {
//        return property("hgrow", value);
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> minHeight(double value) {
//        return property("minHeight", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> prefHeight(double value) {
//        return property("prefHeight", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> maxHeight(double value) {
//        return property("maxHeight", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> minWidth(double value) {
//        return property("minWidth", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> prefWidth(double value) {
//        return property("prefWidth", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> maxWidth(double value) {
//        return property("maxWidth", value);
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> topAnchor(double value) {
//        return property("topAnchor", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> rightAnchor(double value) {
//        return property("rightAnchor", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> bottomAnchor(double value) {
//        return property("bottomAnchor", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> leftAnchor(double value) {
//        return property("leftAnchor", value);
//    }
//
//    public static <ACTION> VProperty<VNode<ACTION>, ACTION> graphic(VNode<ACTION> value) {
//        return property("graphic", value);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> hover(VChangeListener<? super Boolean, ACTION> listener) {
//        return property("hover", listener);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> visible(boolean value) {
//        return property("visible", value);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> focused(boolean value, VChangeListener<? super Boolean, ACTION> listener) {
//        return property("focused", value, listener);
//    }
//    public static <ACTION> VProperty<Boolean, ACTION> focused(boolean value) {
//        return property("focused", value);
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> progress(double value, VChangeListener<? super Double, ACTION> listener) {
//        return property("progress", value, listener);
//    }
//    public static <ACTION> VProperty<Double, ACTION> progress(double value) {
//        return property("progress", value);
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> value(double value, VChangeListener<? super Double, ACTION> listener) {
//        return property("value", value, listener);
//    }
//    public static <ACTION> VProperty<Double, ACTION> value(double value) {
//        return property("value", value);
//    }
//
//    public static <ACTION> VProperty<VNode, ACTION> top(VNode value) {
//        return property("top", value);
//    }
//    public static <ACTION> VProperty<VNode, ACTION> right(VNode value) {
//        return property("right", value);
//    }
//    public static <ACTION> VProperty<VNode, ACTION> bottom(VNode value) {
//        return property("bottom", value);
//    }
//    public static <ACTION> VProperty<VNode, ACTION> left(VNode value) {
//        return property("left", value);
//    }
//    public static <ACTION> VProperty<VNode, ACTION> center(VNode value) {
//        return property("center", value);
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> hgap(double value) {
//        return property("hgap", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> vgap(double value) {
//        return property("vgap", value);
//    }
//
//    public static <ACTION> VProperty<Integer, ACTION> columnIndex(int value) {
//        return property("columnIndex", value);
//    }
//    public static <ACTION> VProperty<Integer, ACTION> rowIndex(int value) {
//        return property("rowIndex", value);
//    }
//
//    public static <ACTION> VProperty<Integer, ACTION> columnSpan(int value) {
//        return property("columnSpan", value);
//    }
//    public static <ACTION> VProperty<Integer, ACTION> rowSpan(int value) {
//        return property("rowSpan", value);
//    }
//
//
//    public static <ACTION> VProperty<HPos, ACTION> halignment(HPos value) {
//        return property("halignment", value);
//    }
//    public static <ACTION> VProperty<VPos, ACTION> valignment(VPos value) {
//        return property("valignment", value);
//    }
//
//    public static <ACTION> VProperty<Border, ACTION> border(Border value) {
//        return property("border", value);
//    }
//    public static <ACTION> VProperty<Border, ACTION> border(Color color, double width) {
//        return border(new Border(new BorderStroke(color, null, null, new BorderWidths(width))));
//    }
//
//    public static <ACTION> VProperty<Background, ACTION> background(Background value) {
//        return property("background", value);
//    }
//    public static <ACTION> VProperty<Background, ACTION> background(Color fill) {
//        return background(new Background(new BackgroundFill(fill, null, null)));
//    }
//
//    public static <ACTION> VProperty<Double, ACTION> max(double value) {
//        return property("max", value);
//    }
//    public static <ACTION> VProperty<Double, ACTION> min(double value) {
//        return property("min", value);
//    }
//
//
//    public static <ACTION> VProperty<Double, ACTION> opacity(double value) {
//        return property("opacity", value);
//    }
//
//
//    public static <ACTION> VProperty<Paint, ACTION> fill(Paint value) {
//        return property("fill", value);
//    }
//    public static <ACTION> VProperty<Paint, ACTION> textFill(Paint value) {
//        return property("textFill", value);
//    }
//
//    public static <ACTION> VProperty<Boolean, ACTION> defaultButton(boolean value) {
//        return property("defaultButton", value);
//    }
//
//
//
//    public static <ACTION> VEventHandlerElement<ActionEvent, ACTION> onAction(VEventHandler<ActionEvent, ACTION> eventHandler) {
//        return onEvent(ACTION, eventHandler);
//    }
//
//    public static <ACTION> VEventHandlerElement<MouseEvent, ACTION> onMouseClicked(VEventHandler<MouseEvent, ACTION> eventHandler) {
//        return onEvent(MOUSE_CLICKED, eventHandler);
//    }
}
