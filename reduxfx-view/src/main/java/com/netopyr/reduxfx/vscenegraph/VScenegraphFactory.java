package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.builders.AccordionBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.*;
import com.netopyr.reduxfx.vscenegraph.builders.ButtonBaseBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ButtonBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.CheckBoxBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ContextMenuBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ControlBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.LabelBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.LabeledBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ListViewBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.MenuBarBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.MenuBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.MenuItemBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ProgressBarBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ProgressIndicatorBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ScrollPaneBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.SliderBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.SplitPaneBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TextFieldBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TextInputControlBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TitledPaneBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.ToggleButtonBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TreeItemBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TreeViewBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TabBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TabPaneBuilder;
import com.netopyr.reduxfx.vscenegraph.javafx.TreeItemWrapper;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import io.vavr.collection.Array;
import io.vavr.collection.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
public class VScenegraphFactory {

    private VScenegraphFactory() {}

    public static StagesBuilder Stages() {
        return new StagesBuilder(Array.empty());
    }

    public static <B extends StageBuilder<B>> StageBuilder<B> Stage() {
        return Factory.node(Stage.class, () -> new StageBuilder<>(Stage.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends SceneBuilder<B>> SceneBuilder<B> Scene() {
        return Factory.node(Scene.class, () -> new SceneBuilder<>(Scene.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends NodeBuilder<B>> NodeBuilder<B> customNode(Class<? extends Node> nodeClass) {
        return Factory.node(nodeClass, () -> new NodeBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends GroupBuilder<B>> GroupBuilder<B> Group() {
        return Factory.node(Group.class, () -> new GroupBuilder<>(Group.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends AccordionBuilder<B>> AccordionBuilder<B> Accordion() {
        return Factory.node(Accordion.class, () -> new AccordionBuilder<B>(Accordion.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends AnchorPaneBuilder<B>> AnchorPaneBuilder<B> AnchorPane() {
        return Factory.node(AnchorPane.class, () -> new AnchorPaneBuilder<B>(AnchorPane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends BorderPaneBuilder<B>> BorderPaneBuilder<B> BorderPane(Class<? extends BorderPane> nodeClass) {
        return Factory.node(nodeClass, () -> new BorderPaneBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends BorderPaneBuilder<B>> BorderPaneBuilder<B> BorderPane() {
        return BorderPane(BorderPane.class);
    }

    public static <B extends ButtonBaseBuilder<B>> ButtonBaseBuilder<B> ButtonBase() {
        return Factory.node(ButtonBase.class, () -> new ButtonBaseBuilder<>(ButtonBase.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ButtonBuilder<B>> ButtonBuilder<B> Button(Class<? extends Button> nodeClass) {
        return Factory.node(nodeClass, () -> new ButtonBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends ButtonBuilder<B>> ButtonBuilder<B> Button() {
        return Button(Button.class);
    }

    public static <B extends CheckBoxBuilder<B>> CheckBoxBuilder<B> CheckBox(Class<? extends CheckBox> nodeClass) {
        return Factory.node(nodeClass, () -> new CheckBoxBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends CheckBoxBuilder<B>> CheckBoxBuilder<B> CheckBox() {
        return CheckBox(CheckBox.class);
    }

    public static <B extends ControlBuilder<B>> ControlBuilder<B> Control() {
        return Factory.node(Control.class, () -> new ControlBuilder<>(Control.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends HBoxBuilder<B>> HBoxBuilder<B> HBox() {
        return Factory.node(HBox.class, () -> new HBoxBuilder<>(HBox.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends LabelBuilder<B>> LabelBuilder<B> Label() {
        return Factory.node(Label.class, () -> new LabelBuilder<>(Label.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends LabeledBuilder<B>> LabeledBuilder<B> Labeled() {
        return Factory.node(Labeled.class, () -> new LabeledBuilder<>(Labeled.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ListViewBuilder<B, T>, T> ListViewBuilder<B, T> ListView(Class<T> elementClass) {
        return Factory.node(ListView.class, () -> new ListViewBuilder<>(ListView.class, elementClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends PaneBuilder<B>> PaneBuilder<B> Pane() {
        return Factory.node(Pane.class, () -> new PaneBuilder<>(Pane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ParentBuilder<B>> ParentBuilder<B> Parent() {
        return Factory.node(Parent.class, () -> new ParentBuilder<>(Parent.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ProgressBarBuilder<B>> ProgressBarBuilder<B> ProgressBar(Class<? extends ProgressBar> nodeClass) {
        return Factory.node(nodeClass, () -> new ProgressBarBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends ProgressBarBuilder<B>> ProgressBarBuilder<B> ProgressBar() {
        return ProgressBar(ProgressBar.class);
    }

    public static <B extends ProgressIndicatorBuilder<B>> ProgressIndicatorBuilder<B> ProgressIndicator() {
        return Factory.node(ProgressIndicator.class, () -> new ProgressIndicatorBuilder<>(ProgressIndicator.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends RegionBuilder<B>> RegionBuilder<B> Region() {
        return Factory.node(Region.class, () -> new RegionBuilder<>(Region.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends SliderBuilder<B>> SliderBuilder<B> Slider() {
        return Factory.node(Slider.class, () -> new SliderBuilder<>(Slider.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends StackPaneBuilder<B>> StackPaneBuilder<B> StackPane() {
        return Factory.node(StackPane.class, () -> new StackPaneBuilder<>(StackPane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends TitledPaneBuilder<B>> TitledPaneBuilder<B> TitledPane() {
        return Factory.node(TitledPane.class, () -> new TitledPaneBuilder<>(TitledPane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends GridPaneBuilder<B>> GridPaneBuilder<B> GridPane() {
        return Factory.node(GridPane.class, () -> new GridPaneBuilder<>(GridPane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ScrollPaneBuilder<B>> ScrollPaneBuilder<B> ScrollPane() {
        return Factory.node(ScrollPane.class, () -> new ScrollPaneBuilder<>(ScrollBar.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends TextBuilder<B>> TextBuilder<B> Text(Class<? extends Text> nodeClass) {
        return Factory.node(nodeClass, () -> new TextBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends TextBuilder<B>> TextBuilder<B> Text() {
        return Text(Text.class);
    }

    public static <B extends TextFieldBuilder<B>> TextFieldBuilder<B> TextField(Class<? extends TextField> nodeClass) {
        return Factory.node(nodeClass, () -> new TextFieldBuilder<>(nodeClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }
    public static <B extends TextFieldBuilder<B>> TextFieldBuilder<B> TextField() {
        return TextField(TextField.class);
    }

    public static <B extends TextInputControlBuilder<B>> TextInputControlBuilder<B> TextInputControl() {
        return Factory.node(TextInputControl.class, () -> new TextInputControlBuilder<>(TextInputControl.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ToggleButtonBuilder<B>> ToggleButtonBuilder<B> ToggleButton() {
        return Factory.node(ToggleButton.class, () -> new ToggleButtonBuilder<>(ToggleButton.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends VBoxBuilder<B>> VBoxBuilder<B> VBox() {
        return Factory.node(VBox.class, () -> new VBoxBuilder<>(VBox.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends RectangleBuilder<B>> RectangleBuilder<B> Rectangle() {
        return Factory.node(Rectangle.class, () -> new RectangleBuilder<>(Rectangle.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends MenuBarBuilder<B>> MenuBarBuilder<B> MenuBar() {
        return Factory.node(MenuBar.class, () -> new MenuBarBuilder<>(MenuBar.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends MenuBuilder<B>> MenuBuilder<B> Menu() {
        return Factory.node(Menu.class, () -> new MenuBuilder<>(Menu.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends MenuItemBuilder<B>> MenuItemBuilder<B> MenuItem() {
        return Factory.node(MenuItem.class, () -> new MenuItemBuilder<>(MenuItem.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends AlertBuilder<B>> AlertBuilder<B> Alert() {
        return Factory.node(Alert.class, () -> new AlertBuilder<>(Alert.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends ContextMenuBuilder<B>> ContextMenuBuilder<B> ContextMenu() {
        return Factory.node(ContextMenu.class, () -> new ContextMenuBuilder<>(ContextMenu.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends TreeViewBuilder<B, E>, E> TreeViewBuilder<B, E> TreeView(Class<E> elementClass) {
        return Factory.node(TreeView.class, () -> new TreeViewBuilder<B, E>(TreeView.class, elementClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends TreeItemBuilder<B, E>, E> TreeItemBuilder<B, E> TreeItem(Class<E> elementClass) {
        return Factory.node(TreeItem.class, () -> new TreeItemBuilder<B, E>(TreeItemWrapper.class, elementClass, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends SplitPaneBuilder<B>> SplitPaneBuilder<B> SplitPane() {
        return Factory.node(SplitPane.class, () -> new SplitPaneBuilder<B>(SplitPane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends TabBuilder<B>> TabBuilder<B> Tab() {
        return Factory.node(Tab.class, () -> new TabBuilder<>(Tab.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

    public static <B extends TabPaneBuilder<B>> TabPaneBuilder<B> TabPane() {
        return Factory.node(TabPane.class, () -> new TabPaneBuilder<>(TabPane.class, HashMap.empty(), HashMap.empty(), HashMap.empty(), HashMap.empty()));
    }

}
