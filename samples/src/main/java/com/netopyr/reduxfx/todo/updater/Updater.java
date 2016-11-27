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
                                newTextFieldChangedAction -> new AppModel(
                                        newTextFieldChangedAction.getText(),
                                        oldState.getTodos(),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(AddTodoAction.class),
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

                        Case(instanceOf(DeleteTodoAction.class),
                                deleteToDoAction -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .filter(todoEntry -> todoEntry.getId() != deleteToDoAction.getId()),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(EditTodoAction.class),
                                editTodoAction -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != editTodoAction.getId() ? entry :
                                                        new TodoEntry(entry.getId(), editTodoAction.getText(), entry.isCompleted(), entry.isHover(), entry.isEditMode())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(CompleteTodoAction.class),
                                completeTodoAction -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != completeTodoAction.getId() ? entry :
                                                        new TodoEntry(entry.getId(), entry.getText(), !entry.isCompleted(), entry.isHover(), entry.isEditMode())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(CompleteAllAction.class),
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

                        Case(instanceOf(SetFilterAction.class),
                                setFilterAction -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos(),
                                        setFilterAction.getFilter()
                                )
                        ),

                        Case(instanceOf(SetTodoHoverAction.class),
                                setTodoHoverAction -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != setTodoHoverAction.getId() ? entry :
                                                        new TodoEntry(entry.getId(), entry.getText(), entry.isCompleted(), setTodoHoverAction.isValue(), entry.isEditMode())
                                                ),
                                        oldState.getFilter()
                                )
                        ),

                        Case(instanceOf(SetEditModeAction.class),
                                setEditModeAction -> new AppModel(
                                        oldState.getNewTodoText(),
                                        oldState.getTodos()
                                                .map(entry -> entry.getId() != setEditModeAction.getId() ? entry :
                                                        new TodoEntry(entry.getId(), entry.getText(), entry.isCompleted(), entry.isHover(), setEditModeAction.isValue())
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
