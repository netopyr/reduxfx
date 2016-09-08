package com.netopyr.reduxfx.todo.reducers;

import com.netopyr.reduxfx.Reducer;
import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.actions.CompleteToDo;
import com.netopyr.reduxfx.todo.actions.DeleteToDo;
import com.netopyr.reduxfx.todo.actions.EditToDo;
import com.netopyr.reduxfx.todo.actions.NewTextFieldChanged;
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
                        state.getTodos()
                );

            case ADD_TODO:
                return new AppModel(
                        "",
                        new ToDoEntry(
                                state.getTodos().stream()
                                        .map(ToDoEntry::getId)
                                        .max((t1, t2) -> t1 - t2)
                                        .orElse(-1) + 1,
                                state.getNewToDoText(),
                                false
                        ),
                        state.getTodos()
                );

            case DELETE_TODO:
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos().stream()
                                .filter(toDoEntry -> toDoEntry.getId() != ((DeleteToDo) action).getId())
                );

            case EDIT_TODO:
                final EditToDo editToDo = (EditToDo) action;
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos().stream()
                                .map(entry -> entry.getId() != editToDo.getId() ? entry :
                                        new ToDoEntry(entry.getId(), editToDo.getText(), entry.isCompleted())
                                )
                );

            case COMPLETE_TODO:
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos().stream()
                                .map(entry -> entry.getId() != ((CompleteToDo) action).getId() ? entry :
                                        new ToDoEntry(entry.getId(), entry.getText(), !entry.isCompleted())
                                )
                );

            case COMPLETE_ALL:
                final boolean areAllMarked = state.getTodos().stream().allMatch(ToDoEntry::isCompleted);
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos().stream()
                                .map(entry -> new ToDoEntry(entry.getId(), entry.getText(), !areAllMarked))
                );

            case CLEAR_COMPLETED:
                return new AppModel(
                        state.getNewToDoText(),
                        state.getTodos().stream()
                                .filter(entry -> !entry.isCompleted())
                );

            default:
                return state;
        }
    }
}
