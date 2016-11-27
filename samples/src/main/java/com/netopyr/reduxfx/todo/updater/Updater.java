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

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class Updater {

    private static final Logger LOG = LoggerFactory.getLogger(Updater.class);

    private Updater() {
    }

    public static AppModel update(AppModel oldState, Action action) {
        if (action == null) {
            return oldState;
        }

        final AppModel newState =
                Match(action).of(

                        Case(instanceOf(NewTextFieldChangedAction.class),
                                newTextFieldChangedAction ->
                                        oldState.withNewTodoText(newTextFieldChangedAction.getText())
                        ),

                        Case(instanceOf(AddTodoAction.class),
                                oldState.withNewTodoText("")
                                        .withTodos(
                                                oldState.getTodos().append(
                                                        new TodoEntry()
                                                                .withId(
                                                                        oldState.getTodos()
                                                                                .map(TodoEntry::getId)
                                                                                .max()
                                                                                .getOrElse(-1) + 1
                                                                )
                                                                .withText(oldState.getNewTodoText())
                                                )
                                        )
                        ),

                        Case(instanceOf(DeleteTodoAction.class),
                                deleteTodoAction -> oldState.withTodos(
                                        oldState.getTodos().filter(
                                                todoEntry -> todoEntry.getId() != deleteTodoAction.getId()
                                        )
                                )
                        ),

                        Case(instanceOf(EditTodoAction.class),
                                editTodoAction -> oldState.withTodos(
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != editTodoAction.getId() ?
                                                        entry : entry.withText(editTodoAction.getText())
                                                )
                                )
                        ),

                        Case(instanceOf(CompleteTodoAction.class),
                                completeTodoAction -> oldState.withTodos(
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != completeTodoAction.getId() ?
                                                        entry : entry.withCompleted(!entry.isCompleted())
                                                )
                                )
                        ),

                        Case(instanceOf(CompleteAllAction.class),
                                completeAllAction -> {
                                    final boolean areAllMarked = oldState.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                                    return oldState.withTodos(
                                            oldState.getTodos()
                                                    .map(entry -> entry.withCompleted(! areAllMarked)));
                                }
                        ),

                        Case(instanceOf(SetFilterAction.class),
                                setFilterAction -> oldState.withFilter(setFilterAction.getFilter())
                        ),

                        Case(instanceOf(SetTodoHoverAction.class),
                                setTodoHoverAction -> oldState.withTodos(
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != setTodoHoverAction.getId() ?
                                                        entry : entry.withHover(setTodoHoverAction.isValue())
                                                )                                )
                        ),

                        Case(instanceOf(SetEditModeAction.class),
                                setEditModeAction -> oldState.withTodos(
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != setEditModeAction.getId() ?
                                                        entry : entry.withEditMode(setEditModeAction.isValue())
                                                )
                                )
                        ),

                        Case($(), oldState)
                );

        LOG.trace("\nUpdater Old State:\n{}\nUpdater Action:\n{}\nUpdater New State:\n{}\n\n",
                oldState, action, newState);
        return newState;
    }
}
