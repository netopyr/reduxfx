package com.netopyr.reduxfx.todo.reducers;

import com.netopyr.reduxfx.Reducer;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.CompleteToDo;
import com.netopyr.reduxfx.todo.actions.DeleteToDo;
import com.netopyr.reduxfx.todo.actions.EditToDo;
import com.netopyr.reduxfx.todo.actions.NewTextFieldChanged;
import com.netopyr.reduxfx.todo.actions.SetFilter;
import com.netopyr.reduxfx.todo.actions.SetToDoHover;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToDos implements Reducer<AppModel, Action> {

    private static final Logger LOG = LoggerFactory.getLogger(ToDos.class);

    @Override
    public AppModel reduce(AppModel oldState, Action action) {
        if (action == null) {
            return oldState;
        }

        LOG.info("\n\n\n\nReducer Old State: " + oldState + "\n\nReducer Action: " + action);

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
                                new ToDoEntry(
                                        oldState.getTodos()
                                                .map(ToDoEntry::getId)
                                                .max()
                                                .getOrElse(-1) + 1,
                                        oldState.getNewToDoText(),
                                        false,
                                        false
                                )
                        ),
                        oldState.getFilter()
                );
                break;

            case DELETE_TODO:
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos()
                                .filter(toDoEntry -> toDoEntry.getId() != ((DeleteToDo) action).getId()),
                        oldState.getFilter()
                );
                break;

            case EDIT_TODO:
                final EditToDo editToDo = (EditToDo) action;
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != editToDo.getId() ? entry :
                                        new ToDoEntry(entry.getId(), editToDo.getText(), entry.isCompleted(), entry.isHover())
                                ),
                        oldState.getFilter()
                );
                break;

            case COMPLETE_TODO:
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != ((CompleteToDo) action).getId() ? entry :
                                        new ToDoEntry(entry.getId(), entry.getText(), !entry.isCompleted(), entry.isHover())
                                ),
                        oldState.getFilter()
                );
                break;

            case COMPLETE_ALL:
                final boolean areAllMarked = oldState.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos()
                                .map(entry -> new ToDoEntry(entry.getId(), entry.getText(), !areAllMarked, entry.isHover())),
                        oldState.getFilter()
                );
                break;

            case CLEAR_COMPLETED:
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos()
                                .filter(entry -> !entry.isCompleted()),
                        oldState.getFilter()
                );
                break;

            case SET_FILTER:
                final SetFilter setFilter = (SetFilter) action;
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos(),
                        setFilter.getFilter()
                );
                break;

            case SET_TODO_HOVER:
                final SetToDoHover setToDoHover = (SetToDoHover) action;
                newState = new AppModel(
                        oldState.getNewToDoText(),
                        oldState.getTodos()
                                .map(entry -> entry.getId() != setToDoHover.getId() ? entry :
                                        new ToDoEntry(entry.getId(), entry.getText(), entry.isCompleted(), setToDoHover.isValue())
                                ),
                        oldState.getFilter()
                );
                break;

            default:
                newState = oldState;
                break;
        }

        LOG.info("\n\nReducer New State: " + newState);
        return newState;
    }
}
