package com.netopyr.reduxfx.todo.updater;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.AddTodoAction;
import com.netopyr.reduxfx.todo.actions.CompleteAllAction;
import com.netopyr.reduxfx.todo.actions.CompleteTodoAction;
import com.netopyr.reduxfx.todo.actions.DeleteTodoAction;
import com.netopyr.reduxfx.todo.actions.EditTodoAction;
import com.netopyr.reduxfx.todo.actions.NewTextFieldChangedAction;
import com.netopyr.reduxfx.todo.actions.SetEditModeAction;
import com.netopyr.reduxfx.todo.actions.SetFilterAction;
import com.netopyr.reduxfx.todo.actions.SetTodoHoverAction;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    private Updater() {
    }

    public static AppModel update(AppModel state, Action action) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
        Objects.requireNonNull(action, "The parameter 'action' must not be null");

        final AppModel newState =
                Match(action).of(

                        Case(instanceOf(NewTextFieldChangedAction.class),
                                newTextFieldChangedAction ->
                                        state.withNewTodoText(newTextFieldChangedAction.getText())
                        ),

                        Case(instanceOf(AddTodoAction.class),
                                state.withNewTodoText("")
                                        .withTodos(
                                                state.getTodos().append(
                                                        new TodoEntry()
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

                        Case(instanceOf(DeleteTodoAction.class),
                                deleteTodoAction -> state.withTodos(
                                        state.getTodos().filter(
                                                todoEntry -> todoEntry.getId() != deleteTodoAction.getId()
                                        )
                                )
                        ),

                        Case(instanceOf(EditTodoAction.class),
                                editTodoAction -> state.withTodos(
                                        state.getTodos()
                                                .map(entry -> entry.getId() != editTodoAction.getId() ?
                                                        entry : entry.withText(editTodoAction.getText())
                                                )
                                )
                        ),

                        Case(instanceOf(CompleteTodoAction.class),
                                completeTodoAction -> state.withTodos(
                                        state.getTodos()
                                                .map(entry -> entry.getId() != completeTodoAction.getId() ?
                                                        entry : entry.withCompleted(!entry.isCompleted())
                                                )
                                )
                        ),

                        Case(instanceOf(CompleteAllAction.class),
                                completeAllAction -> {
                                    final boolean areAllMarked = state.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                                    return state.withTodos(
                                            state.getTodos()
                                                    .map(entry -> entry.withCompleted(! areAllMarked)));
                                }
                        ),

                        Case(instanceOf(SetFilterAction.class),
                                setFilterAction -> state.withFilter(setFilterAction.getFilter())
                        ),

                        Case(instanceOf(SetTodoHoverAction.class),
                                setTodoHoverAction -> state.withTodos(
                                        state.getTodos()
                                                .map(entry -> entry.getId() != setTodoHoverAction.getId() ?
                                                        entry : entry.withHover(setTodoHoverAction.isValue())
                                                )                                )
                        ),

                        Case(instanceOf(SetEditModeAction.class),
                                setEditModeAction -> state.withTodos(
                                        state.getTodos()
                                                .map(entry -> entry.getId() != setEditModeAction.getId() ?
                                                        entry : entry.withEditMode(setEditModeAction.isValue())
                                                )
                                )
                        ),

                        Case($(), state)
                );

        LOG.trace("\nUpdater Old State:\n{}\nUpdater Action:\n{}\nUpdater New State:\n{}\n\n",
                state, action, newState);
        return newState;
    }
}
