package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

class ItemView {

    static VNode<Action> ItemView(TodoEntry todoEntry) {
        return HBox(
                alignment(Pos.CENTER_LEFT),
                minWidth(Region.USE_PREF_SIZE),
                minHeight(Region.USE_PREF_SIZE),
                styleClass("item_root"),
                stylesheets("itemview.css"),
                CheckBox(
                        mnemonicParsing(false),
                        selected(todoEntry.isCompleted()),
                        onAction(e -> Actions.completeTodo(todoEntry.getId()))
                ),
                StackPane(
                        alignment(Pos.CENTER_LEFT),
                        hgrow(Priority.ALWAYS),
                        HBox(
                                visible(! todoEntry.isEditMode()),
                                styleClass("content_box"),
                                hover((oldValue, newValue) -> Actions.setTodoHover(todoEntry.getId(), Boolean.TRUE.equals(newValue))),
                                Label(
                                        maxWidth(Double.MAX_VALUE),
                                        maxHeight(Double.MAX_VALUE),
                                        text(todoEntry.getText()),
                                        hgrow(Priority.ALWAYS),
                                        styleClass("label", todoEntry.isCompleted() ? "strikethrough" : ""),
                                        onMouseClicked(e -> e.getClickCount() > 1 ? Actions.setEditMode(todoEntry.getId(), true) : null)
                                ),
                                Button(
                                        visible(todoEntry.isHover()),
                                        graphic(
                                                node(FontAwesomeIconView.class,
                                                        property("glyphName", "CLOSE"),
                                                        property("size", "1.5em"),
                                                        styleClass("close_icon")
                                                )
                                        ),
                                        onAction(e -> Actions.deleteTodo(todoEntry.getId()))
                                )
                        ),
                        TextField(
                                visible(todoEntry.isEditMode()),
                                promptText("What needs to be done?"),
                                focused(todoEntry.isEditMode(), (oldValue, newValue) -> newValue ? null : Actions.setEditMode(todoEntry.getId(), false)),
                                text(todoEntry.getText(), ((oldValue, newValue) -> Actions.editTodo(todoEntry.getId(), newValue))),
                                onAction(e -> Actions.setEditMode(todoEntry.getId(), false))
                        )
                )
        );
    }
}
