package com.netopyr.reduxfx.examples.todo.updater;

import com.netopyr.reduxfx.examples.todo.actions.AddTodoAction;
import com.netopyr.reduxfx.examples.todo.actions.CompleteAllAction;
import com.netopyr.reduxfx.examples.todo.actions.CompleteTodoAction;
import com.netopyr.reduxfx.examples.todo.actions.DeleteTodoAction;
import com.netopyr.reduxfx.examples.todo.actions.EditTodoAction;
import com.netopyr.reduxfx.examples.todo.actions.NewTextFieldChangedAction;
import com.netopyr.reduxfx.examples.todo.actions.SetEditModeAction;
import com.netopyr.reduxfx.examples.todo.actions.SetFilterAction;
import com.netopyr.reduxfx.examples.todo.actions.SetTodoHoverAction;
import com.netopyr.reduxfx.examples.todo.state.AppModel;
import com.netopyr.reduxfx.examples.todo.state.TodoEntry;
import javaslang.API;

import java.util.Objects;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

/**
 * The {@code Updater} is the heart of every ReduxFX-application. This is where the main application logic resides.
 * <p>
 * An {@code Updater} consists of a single function ({@link #update(AppModel, Object)} in this class), which takes
 * the current state (an instance of {@link AppModel}) and an {@link Action} and calculates the new state from that.
 * <p>
 * Please note that {@code Updater} has no internal state. Everything that is needed for {@code update} is passed in
 * the parameters.
 */
public class Updater {

    private Updater() {
    }

    /**
     * The method {@code update} is the central piece of the TodoMVC-application. The whole application logic is
     * implemented here.
     * <p>
     * This method takes the current state (an instance of {@link AppModel}) and an {@link Action} and calculates the
     * new state from that.
     * <p>
     * Please note that {@code update} does not require any internal state. Everything that is needed, is passed in the
     * parameters. Also {@code update} has no side effects. It is a pure function.
     * <p>
     * Also please note, that {@code AppModel} is an immutable data structure. This means that {@code update} does not
     * modify the old state, but instead creates a new instance of {@code AppModel}, if anything changes.
     *
     * @param state  the current state
     * @param action the {@code Action} that needs to be performed
     * @return the new state
     * @throws NullPointerException if state or action are {@code null}
     */
    public static AppModel update(AppModel state, Object action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        // This is part of Javaslang's pattern-matching API. It works similar to the regular switch-case
        // in Java, except that it is much more flexible and that it can be used as an expression.
        // We check which of the cases is true and in that branch we specify the value that will be assigned
        // to newState.
        return Match(action).of(

                // If the action is a NewTextFieldChangedAction, we return a new AppModel with the
                // property newTodoText set to the new value
                API.Case(instanceOf(NewTextFieldChangedAction.class),
                        newTextFieldChangedAction ->
                                state.withNewTodoText(newTextFieldChangedAction.getText())
                ),

                // If the action is an AddTodoAction, we append a new TodoEntry to the list of todo-entries.
                // The new TodoEntry will get an id that is the maximum of all used ids plus one and the
                // text will be set to the value stored in property newTodoText of the current AppModel.
                // In addition we clear the property newTodoText.
                Case(instanceOf(AddTodoAction.class),
                        state.withNewTodoText("")
                                .withTodos(
                                        state.getTodos().append(
                                                TodoEntry.create()
                                                        .withId(
                                                                state.getTodos()
                                                                        .map(TodoEntry::getId)
                                                                        .max()
                                                                        .getOrElse(-1) + 1
                                                        )
                                                        .withText(state.getNewTodoText())
                                        )
                                )
                ),

                // If the action is a DeleteTodoAction, we create a new AppModel, where the TodoEntry that
                // should be deleted is filtered out of the list of todo-entries.
                Case(instanceOf(DeleteTodoAction.class),
                        deleteTodoAction -> state.withTodos(
                                state.getTodos().filter(
                                        todoEntry -> todoEntry.getId() != deleteTodoAction.getId()
                                )
                        )
                ),

                // If the action is an EditTodoAction, we need to replace the TodoEntry with the given id with
                // one that uses the given text.
                // We do that by mapping all items in the todos-list. If the item-id does not match we re-use
                // the old entry. But if the id matches, we replace the TodoEntry with one that has the text
                // set to the given value.
                API.Case(instanceOf(EditTodoAction.class),
                        editTodoAction -> state.withTodos(
                                state.getTodos()
                                        .map(entry -> entry.getId() != editTodoAction.getId() ?
                                                entry : entry.withText(editTodoAction.getText())
                                        )
                        )
                ),

                // If the action is a CompleteTodoAction, we need to replace the TodoEntry with the given id
                // with one where the completed-flag is toggled.
                // We do that by mapping all items in the todos-list. If the item-id does not match
                // we re-use the old entry. But if the id matches, we replace the TodoEntry with one
                // where the completed flag is toggled.
                API.Case(instanceOf(CompleteTodoAction.class),
                        completeTodoAction -> state.withTodos(
                                state.getTodos()
                                        .map(entry -> entry.getId() != completeTodoAction.getId() ?
                                                entry : entry.withCompleted(!entry.isCompleted())
                                        )
                        )
                ),

                // If the action is a CompleteAllAction, we need to set the completed-flag of all todo-entries.
                // First we calculate, if the completed-flags need to be set or cleared. This will be stored in
                // the variable areAllMarked. The completed-flags need to be set, if at least one of them
                // is not set. If all of the flags are set already, we need to clear them.
                API.Case(instanceOf(CompleteAllAction.class),
                        completeAllAction -> {
                            final boolean areAllMarked = state.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                            return state.withTodos(
                                    state.getTodos()
                                            .map(entry -> entry.withCompleted(!areAllMarked)));
                        }
                ),

                // If the action is a SetFilterAction, we need to return a new AppModel where the filter is
                // set to the given value.
                Case(instanceOf(SetFilterAction.class),
                        setFilterAction -> state.withFilter(setFilterAction.getFilter())
                ),

                // If the action is an SetTodoHoverAction, we need to replace the TodoEntry with the given id
                // with one that uses the given hover-value.
                // We do that by mapping all items in the todos-list. If the item-id does not match we re-use
                // the old entry. But if the id matches, we replace the TodoEntry with one that has the
                // hover-flag set to the given value.
                API.Case(instanceOf(SetTodoHoverAction.class),
                        (SetTodoHoverAction setTodoHoverAction) -> state.withTodos(
                                state.getTodos()
                                        .map(entry -> entry.getId() != setTodoHoverAction.getId() ?
                                                entry : entry.withHover(setTodoHoverAction.isValue())
                                        )
                        )
                ),

                // If the action is an SetEditModeAction, we need to replace the TodoEntry with the given id
                // with one that uses the given editMode-value.
                // We do that by mapping all items in the todos-list. If the item-id does not match we re-use
                // the old entry. But if the id matches, we replace the TodoEntry with one that has the
                // editMode-flag set to the given value.
                API.Case(instanceOf(SetEditModeAction.class),
                        (SetEditModeAction setEditModeAction) -> state.withTodos(
                                state.getTodos()
                                        .map(entry -> entry.getId() != setEditModeAction.getId() ?
                                                entry : entry.withEditMode(setEditModeAction.isValue())
                                        )
                        )
                ),

                // This is the default branch of this switch-case. If an unknown Action was passed to the
                // updater, we simple return the old state. This is a convention, that is not needed right
                // now, but will help once you start to decompose your updater.
                Case($(), state)
        );
    }
}
