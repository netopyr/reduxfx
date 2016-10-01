package com.netopyr.reduxfx.todo.reducers;

import com.netopyr.reduxfx.Reducer;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.CompleteToDo;
import com.netopyr.reduxfx.todo.actions.DeleteToDo;
import com.netopyr.reduxfx.todo.actions.EditToDo;
import com.netopyr.reduxfx.todo.actions.NewTextFieldChanged;
import com.netopyr.reduxfx.todo.actions.SetFilter;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;

public class ToDos implements Reducer<AppModel, Action> {

    @Override
    public AppModel reduce(AppModel state, Action action) {
        if (action == null) {
            return state;
        }

        switch (action.getType()) {
            case NEW_TEXTFIELD_CHANGED:
                return new AppModel(
                        ((NewTextFieldChanged) action).getText(),
                        state.getTodos(),
                        state.getFilter()
                );

            case ADD_TODO:
                return new AppModel(
                        "",
                        state.getTodos().append(
                                new ToDoEntry(
                                        state.getTodos()
                                                .map(ToDoEntry::getId)
                                                .max()
                                                .getOrElse(-1) + 1,
                                        state.getNewToDoText(),
                                        false
                                )
                        ),
                        state.getFilter()
                );

            case DELETE_TODO:
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos()
                                .filter(toDoEntry -> toDoEntry.getId() != ((DeleteToDo) action).getId()),
                        state.getFilter()
                );

            case EDIT_TODO:
                final EditToDo editToDo = (EditToDo) action;
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos()
                                .map(entry -> entry.getId() != editToDo.getId() ? entry :
                                        new ToDoEntry(entry.getId(), editToDo.getText(), entry.isCompleted())
                                ),
                        state.getFilter()
                );

            case COMPLETE_TODO:
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos()
                                .map(entry -> entry.getId() != ((CompleteToDo) action).getId() ? entry :
                                        new ToDoEntry(entry.getId(), entry.getText(), !entry.isCompleted())
                                ),
                        state.getFilter()
                );

            case COMPLETE_ALL:
                final boolean areAllMarked = state.getTodos().find(entry -> !entry.isCompleted()).isEmpty();
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos()
                                .map(entry -> new ToDoEntry(entry.getId(), entry.getText(), !areAllMarked)),
                        state.getFilter()
                );

            case CLEAR_COMPLETED:
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos()
                                .filter(entry -> !entry.isCompleted()),
                        state.getFilter()
                );

            case SET_FILTER:
                final SetFilter setFilter = (SetFilter) action;
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos(),
                        setFilter.getFilter()
                );

            default:
                return state;
        }
    }
}
