package com.netopyr.reduxfx.todo;

import com.netopyr.reduxfx.View;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.Filter;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.VScenegraphFactory.*;

class ToDoView implements View<AppModel, Action> {

    public VNode<Action> view(AppModel state) {

        return
                StackPane(
                        VBox(
                                HBox(
                                        padding(10.0, 10.0),
                                        spacing(10.0),
                                        TextField(
                                                text(
                                                        state.getNewToDoText(),
                                                        (oldValue, newValue) -> Actions.newTextFieldChanged(newValue)
                                                )
                                        ),
                                        Button(
                                                text("Create"),
                                                disable(state.getNewToDoText().isEmpty()),
                                                onAction(e -> Actions.addToDo())
                                        )
                                ),
                                ListView(
                                        items(state.getTodos()
                                                .filter(toDoEntry -> {
                                                    switch (state.getFilter()) {
                                                        case ACTIVE:
                                                            return ! toDoEntry.isCompleted();
                                                        case COMPLETED:
                                                            return toDoEntry.isCompleted();
                                                        default:
                                                            return true;
                                                    }
                                                })
                                                .map(ToDoEntry::getText))
                                ),
                                HBox(
                                        padding(10.0, 10.0),
                                        spacing(10.0),
                                        Label(
                                                text(String.format("%d item(s) left", state.getTodos().count(toDoEntry -> !toDoEntry.isCompleted())))
                                        ),
                                        ToggleButton(
                                                text("All"),
                                                selected(state.getFilter() == Filter.ALL),
                                                toggleGroup("FILTER_BUTTON_GROUP"),
                                                onAction(e -> Actions.setFilter(Filter.ALL))
                                        ),
                                        ToggleButton(
                                                text("Active"),
                                                selected(state.getFilter() == Filter.ACTIVE),
                                                toggleGroup("FILTER_BUTTON_GROUP"),
                                                onAction(e -> Actions.setFilter(Filter.ACTIVE))
                                        ),
                                        ToggleButton(
                                                text("Completed"),
                                                selected(state.getFilter() == Filter.COMPLETED),
                                                toggleGroup("FILTER_BUTTON_GROUP"),
                                                onAction(e -> Actions.setFilter(Filter.COMPLETED))
                                        )
                                )
                        )
                );
    }
}
