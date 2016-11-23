package com.netopyr.reduxfx.todo.updater;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.AddTodo;
import com.netopyr.reduxfx.todo.actions.CompleteAll;
import com.netopyr.reduxfx.todo.actions.CompleteTodo;
import com.netopyr.reduxfx.todo.actions.DeleteTodo;
import com.netopyr.reduxfx.todo.actions.EditTodo;
import com.netopyr.reduxfx.todo.actions.NewTextFieldChanged;
import com.netopyr.reduxfx.todo.actions.SetEditMode;
import com.netopyr.reduxfx.todo.actions.SetFilter;
import com.netopyr.reduxfx.todo.actions.SetTodoHover;
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

                        Case(instanceOf(NewTextFieldChanged.class),
                                newTextFieldChanged -> new AppModel(
                                        newTextFieldChanged.getText(),
                                        oldState.getTodos(),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(AddTodo.class),
                                new AppModel(
                                        "",
                                        oldState.getTodos().append(
                                                new TodoEntry(
                                                        oldState.getTodos()
                                                                .map(TodoEntry::getId)
                                                                .max()
                                                                .getOrElse(-1) + 1,
                                                        oldState.getNewTodoText(),
                                                        false,
                                                        false,
                                                        false
                                                )
                                        ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(DeleteTodo.class),
                                deleteToDo -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .filter(todoEntry -> todoEntry.getId() != deleteToDo.getId()),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(EditTodo.class),
                                editTodo -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != editTodo.getId() ? entry :
                                                        new TodoEntry(entry.getId(), editTodo.getText(), entry.isCompleted(), entry.isHover(), entry.isEditMode())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(CompleteTodo.class),
                                completeTodo -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != completeTodo.getId() ? entry :
                                                        new TodoEntry(entry.getId(), entry.getText(), !entry.isCompleted(), entry.isHover(), entry.isEditMode())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(CompleteAll.class),
                                completeAll -> {
                                    final boolean areAllMarked = oldState.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                                    return new AppModel(
                                            oldState.getNewTodoText(),
                                            oldState.getTodos()
                                                    .map(entry -> new TodoEntry(entry.getId(), entry.getText(), !areAllMarked, entry.isHover(), entry.isEditMode())),
                                            oldState.getFilter()
                                    );

                                }
                        ),

                        Case(instanceOf(SetFilter.class),
                                setFilter -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos(),
                                        setFilter.getFilter()
                                )
                        ),

                        Case(instanceOf(SetTodoHover.class),
                                setTodoHover -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != setTodoHover.getId() ? entry :
                                                        new TodoEntry(entry.getId(), entry.getText(), entry.isCompleted(), setTodoHover.isValue(), entry.isEditMode())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(SetEditMode.class),
                                setEditMode -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != setEditMode.getId() ? entry :
                                                        new TodoEntry(entry.getId(), entry.getText(), entry.isCompleted(), entry.isHover(), setEditMode.isValue())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case($(), oldState)
                );

        LOG.trace("\nUpdater Old State:\n{}\nUpdater Action:\n{}\nUpdater New State:\n{}\n\n",
                oldState, action, newState);
        return newState;
    }
}
