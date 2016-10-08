package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.CheckBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TextField;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.alignment;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.hgrow;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.id;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.maxHeight;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.maxWidth;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minHeight;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minWidth;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.mnemonicParsing;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.promptText;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.styleClass;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.stylesheets;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.text;

public class MainView implements View<AppModel, Action> {

    public VNode<Action> view(AppModel state) {

        return
                VBox(
                        alignment(Pos.CENTER),
                        minHeight(Region.USE_PREF_SIZE),
                        maxHeight(Region.USE_PREF_SIZE),
                        minWidth(Region.USE_PREF_SIZE),
                        maxWidth(Region.USE_PREF_SIZE),
                        Label(
                                id("title"),
                                text("todos")
                        ),
                        HBox(
                                styleClass("add_item_root"),
                                alignment(Pos.CENTER_LEFT),
                                CheckBox(
                                        id("selectAll"),
                                        mnemonicParsing(false)
                                ),
                                TextField(
                                        id("addInput"),
                                        promptText("What needs to be done?"),
                                        hgrow(Priority.ALWAYS)
                                )
                        ),
                        stylesheets("main.css")
                );
    }
}
