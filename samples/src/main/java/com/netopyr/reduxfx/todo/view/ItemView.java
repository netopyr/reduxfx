package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

class ItemView {

    static VNode<Action> ItemView(ToDoEntry toDoEntry) {
        return HBox(
                alignment(Pos.CENTER_LEFT),
                minWidth(Region.USE_PREF_SIZE),
                minHeight(Region.USE_PREF_SIZE),
                styleClass("item_root"),
                stylesheets("itemview.css"),
                CheckBox(
                        mnemonicParsing(false),
                        selected(toDoEntry.isCompleted()),
                        onAction(e -> Actions.completeToDo(toDoEntry.getId()))
                ),
                StackPane(
                        alignment(Pos.CENTER_LEFT),
                        hgrow(Priority.ALWAYS),
                        toDoEntry.isEditMode() ?
                                TextField(
                                        promptText("What needs to be done?"),
                                        focused(toDoEntry.isEditMode(), (oldValue, newValue) -> newValue ? null : Actions.setEditMode(toDoEntry.getId(), false)),
                                        text(toDoEntry.getText(), ((oldValue, newValue) -> Actions.editToDo(toDoEntry.getId(), newValue))),
                                        onAction(e -> Actions.setEditMode(toDoEntry.getId(), false))
                                )
                                :
                                HBox(
                                        styleClass("content_box"),
                                        hover((oldValue, newValue) -> Actions.setToDoHover(toDoEntry.getId(), Boolean.TRUE.equals(newValue))),
                                        Label(
                                                maxWidth(Double.MAX_VALUE),
                                                maxHeight(Double.MAX_VALUE),
                                                text(toDoEntry.getText()),
                                                hgrow(Priority.ALWAYS),
                                                styleClass(toDoEntry.isCompleted() ? "strikethrough" : ""),
                                                onMouseClicked(e -> e.getClickCount() > 1 ? Actions.setEditMode(toDoEntry.getId(), true) : null)
                                        ),
                                        Button(
                                                visible(toDoEntry.isHover()),
                                                graphic(
                                                        node(FontAwesomeIconView.class,
                                                                property("glyphName", "CLOSE"),
                                                                property("size", "1.5em"),
                                                                styleClass("close_icon")
                                                        )
                                                ),
                                                onAction(e -> Actions.deleteToDo(toDoEntry.getId()))
                                        )
                                )
                )
        );
    }
}
