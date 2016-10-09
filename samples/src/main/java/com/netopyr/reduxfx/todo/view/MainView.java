package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

public class MainView implements View<AppModel, Action> {

    public VNode<Action> view(AppModel state) {

        return
                VBox(
                        alignment(Pos.CENTER),
                        minWidth(Region.USE_PREF_SIZE),
                        minHeight(Region.USE_PREF_SIZE),
                        maxWidth(Region.USE_PREF_SIZE),
                        maxHeight(Region.USE_PREF_SIZE),
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
                        AnchorPane(
                                minWidth(Region.USE_PREF_SIZE),
                                minHeight(Region.USE_PREF_SIZE),
                                maxWidth(Double.MAX_VALUE),
                                maxHeight(Double.MAX_VALUE),
                                ListView(
                                        id("items"),
                                        topAnchor(0.0),
                                        rightAnchor(0.0),
                                        bottomAnchor(0.0),
                                        leftAnchor(0.0),
                                        items(state.getTodos().map(ToDoEntry::getText))
                                )
                        ),
                        stylesheets("main.css")
                );
    }
}
