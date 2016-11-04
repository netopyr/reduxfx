package com.netopyr.reduxfx.todo.updater;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.Actions.CompleteTodo;
import com.netopyr.reduxfx.todo.actions.Actions.DeleteTodo;
import com.netopyr.reduxfx.todo.actions.Actions.EditTodo;
import com.netopyr.reduxfx.todo.actions.Actions.NewTextFieldChanged;
import com.netopyr.reduxfx.todo.actions.Actions.SetEditMode;
import com.netopyr.reduxfx.todo.actions.Actions.SetFilter;
import com.netopyr.reduxfx.todo.actions.Actions.SetTodoHover;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Todos {

    private static final Logger LOG = LoggerFactory.getLogger(Todos.class);

    private Todos() {}

    public static AppModel update(AppModel oldState, Action action) {
        if (action == null) {
            return oldState;
        }

        LOG.trace("\n\n\n\nReducer Old State: " + oldState + "\n\nReducer Action: " + action);

        final AppModel newState;
        switch (action.getType()) {
            case NEW_TEXTFIELD_CHANGED:
                newState = new AppModel(
                        ((NewTextFieldChanged) action).getText(),
                        oldState.getTodos(),
                        oldState.getFilter()
                );
                break;

            case ADD_TODO:
                newState = new AppModel(
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
                );
                break;

            case DELETE_TODO:
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .filter(todoEntry -> todoEntry.getId() != ((DeleteTodo) action).getId()),
                        oldState.getFilter()
                );
                break;

            case EDIT_TODO:
                final EditTodo editTodo = (EditTodo) action;
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != editTodo.getId() ? entry :
                                        new TodoEntry(entry.getId(), editTodo.getText(), entry.isCompleted(), entry.isHover(), entry.isEditMode())
                                ),
                        oldState.getFilter()
                );
                break;

            case COMPLETE_TODO:
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != ((CompleteTodo) action).getId() ? entry :
                                        new TodoEntry(entry.getId(), entry.getText(), !entry.isCompleted(), entry.isHover(), entry.isEditMode())
                                ),
                        oldState.getFilter()
                );
                break;

            case COMPLETE_ALL:
                final boolean areAllMarked = oldState.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .map(entry -> new TodoEntry(entry.getId(), entry.getText(), !areAllMarked, entry.isHover(), entry.isEditMode())),
                        oldState.getFilter()
                );
                break;

            case CLEAR_COMPLETED:
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .filter(entry -> !entry.isCompleted()),
                        oldState.getFilter()
                );
                break;

            case SET_FILTER:
                final SetFilter setFilter = (SetFilter) action;
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos(),
                        setFilter.getFilter()
                );
                break;

            case SET_TODO_HOVER:
                final SetTodoHover setTodoHover = (SetTodoHover) action;
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != setTodoHover.getId() ? entry :
                                        new TodoEntry(entry.getId(), entry.getText(), entry.isCompleted(), setTodoHover.isValue(), entry.isEditMode())
                                ),
                        oldState.getFilter()
                );
                break;

            case SET_EDIT_MODE:
                final SetEditMode setEditMode = (SetEditMode) action;
                newState = new AppModel(
                        oldState.getNewTodoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != setEditMode.getId() ? entry :
                                        new TodoEntry(entry.getId(), entry.getText(), entry.isCompleted(), entry.isHover(), setEditMode.isValue())
                                ),
                        oldState.getFilter()
                );
                break;

            default:
                newState = oldState;
                break;
        }

        LOG.trace("\n\nReducer New State: " + newState);
        return newState;
    }
}
