package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

public class MainView implements View<AppModel, Action> {

    public VNode<Action> view(AppModel state) {

        return
                VBox(
                        alignment(Pos.CENTER),
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
                                        promptText("What needs to be done?")
//                                        hrow(ALWAYS)
                                )
                        ),
                        stylesheets("main.css")
                );
    }
}
