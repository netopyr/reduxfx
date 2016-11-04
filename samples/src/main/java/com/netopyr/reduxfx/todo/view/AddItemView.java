package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

class AddItemView {

    static VNode<Action> AddItemView(AppModel state) {
        return HBox(
                styleClass("add_item_root"),
                alignment(Pos.CENTER_LEFT),
                stylesheets("additem.css"),
                CheckBox(
                        id("selectAll"),
                        mnemonicParsing(false),
                        visible(state.getTodos().nonEmpty()),
                        selected(state.getTodos().nonEmpty() &&
                                state.getTodos()
                                .map(TodoEntry::isCompleted)
                                .fold(true, (a, b) -> a && b)),
                        onAction(e -> Actions.completeAll())
                ),
                TextField(
                        id("addInput"),
                        text(state.getNewTodoText(), (oldValue, newValue) -> Actions.newTextFieldChanged(newValue)),
                        promptText("What needs to be done?"),
                        hgrow(Priority.ALWAYS),
                        onAction(e -> Actions.addTodo())
                )
        );
    }
}
