package com.netopyr.reduxfx.examples.todo.view;

import com.netopyr.reduxfx.examples.todo.state.AppState;
import com.netopyr.reduxfx.examples.todo.actions.Actions;
import com.netopyr.reduxfx.examples.todo.state.TodoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.CheckBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TextField;

/**
 * The class {@code AddItemView} calculates a component consisting of the completeAll-{@code Checkbox} and a
 * {@code TextField} that allows to add new todo-entires.
 */
class AddItemView {

    private AddItemView() {}

    /**
     * The method {@code AddItemView} calculates a new AddItem-component for the given state.
     * <p>
     * This creates a new {@code HBox}, which contains the completeAll-{@code Checkbox} and the {@code TextField}.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created part of the VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    static VNode AddItemView(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return HBox()
                .styleClass("add_item_root")
                .alignment(Pos.CENTER_LEFT)
                .stylesheets(AddItemView.class.getResource("additem.css").toString())
                .children(
                        CheckBox()
                                .id("selectAll")
                                .mnemonicParsing(false)
                                // The Checkbox should only be visible, if there is at least one todo-entry
                                .visible(state.getTodos().nonEmpty())
                                // The Checkbox should be selected, if all todo-entries are completed
                                .selected(state.getTodos().nonEmpty() &&
                                        state.getTodos()
                                                .map(TodoEntry::isCompleted)
                                                .fold(true, (a, b) -> a && b))
                                // This is how an event-lister is defined. The EventListener get the event and has to return
                                // the Action that should be dispatched to the Updater.
                                // If the onAction-event is fired, we want to dispatch a CompleteAllAction.
                                .onAction(e -> Actions.completeAll()),
                        TextField()
                                .id("addInput")
                                // This is how a property value and a ChangeListener can be set.
                                // The ChangeListener gets the old and the new value and has to return the Action that should
                                // be dispatched to the Updater.
                                // We want to set the value of the TextField (first parameter) and fire an
                                // NewTextFieldChangedAction, if the user changes the value (second parameter).
                                .text(state.getNewTodoText(), (oldValue, newValue) -> Actions.newTextFieldChanged(newValue))
                                .promptText("What needs to be done?")
                                .hgrow(Priority.ALWAYS)
                                // If the onAction-event is fired, we want to dispatch an AddTodoAction to add a new todo-entry.
                                .onAction(e -> Actions.addTodo())
                );
    }
}
