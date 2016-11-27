package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;

/**
 * The class {@code Actions} contains factory-methods for all {@link Action}s that are available in this application.
 */
public final class Actions {

    private Actions() {}



    /**
     * This method generates an {@link AddTodoAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when a new
     * {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be created and added to the list.
     *
     * @return the {@code AddTodoAction}
     */
    public static Action addTodo() {
        return new AddTodoAction();
    }



    /**
     * This method generates a {@link DeleteTodoAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when a
     * {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be deleted.
     *
     * @param id the {@code id} of the {@code TodoEntry} that needs to be deleted
     * @return the {@code DeleteTodoAction}
     */
    public static Action deleteTodo(int id) {
        return new DeleteTodoAction(id);
    }



    /**
     * This method generates a {@link EditTodoAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the {@code text} of a
     * {@link com.netopyr.reduxfx.todo.state.TodoEntry} needs to be changed
     *
     * @param id the {@code id} of the {@code TodoEntry} which {@code text} needs to be changed
     * @param text the new {@code text}
     * @return the {@code EditTodoAction}
     */
    public static Action editTodo(int id, String text) {
        return new EditTodoAction(id, text);
    }



    /**
     * This method generates a {@link CompleteTodoAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the
     * {@code completed}-flag of a {@link com.netopyr.reduxfx.todo.state.TodoEntry} needs to be toggled.
     *
     * @param id the {@code id} of the {@code TodoEntry} which {@code completed}-flag needs to be toggled
     * @return the {@code CompleteTodoAction}
     */
    public static Action completeTodo(int id) {
        return new CompleteTodoAction(id);
    }



    /**
     * This method generates a {@link CompleteAllAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when all
     * {@code completed}-flags need to be toggled.
     *
     * @return the {@code CompleteAllAction}
     */
    public static Action completeAll() {
        return new CompleteAllAction();
    }



    /**
     * This method generates a {@link NewTextFieldChangedAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the value of the
     * {@link javafx.scene.control.TextField} for new {@link com.netopyr.reduxfx.todo.state.TodoEntry}s has changed.
     *
     * @param text the new value of the {@code TextField}
     * @return the {@code NewTextFieldChangedAction}
     */
    public static Action newTextFieldChanged(String text) {
        return new NewTextFieldChangedAction(text);
    }



    /**
     * This method generates a {@link SetFilterAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the filter should be
     * changed.
     *
     * @param filter the new {@link Filter}-value
     * @return the {@code SetFilterAction}
     */
    public static Action setFilter(Filter filter) {
        return new SetFilterAction(filter);
    }



    /**
     * This method generates a {@link SetTodoHoverAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the
     * {@code hover}-flag of a {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be changed. The
     * {@code hover}-flag signals, if the mouse hovers over the item.
     *
     * @param id the {@code id} of the {@code TodoEntry} which {@code hover}-flag should be changed
     * @param value the new value of the {@code hover}-flag
     * @return the {@code SetTodoHoverAction}
     */
    public static Action setTodoHover(int id, boolean value) {
        return new SetTodoHoverAction(id, value);
    }



    /**
     * This method generates a {@link SetEditModeAction}.
     *
     * This {@link Action} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the {@code edit}-flag
     * of a {@link com.netopyr.reduxfx.todo.state.TodoEntry} should be changed. The {@code edit}-flag signals, if the
     * item is in editing- or readonly-mode.
     *
     * @param id the {@code id} of the {@code TodoEntry} which {@code edit}-flag should be changed
     * @param value the new value of the {@code edit}-flag
     * @return the {@code SetEditModeAction}
     */
    public static Action setEditMode(int id, boolean value) {
        return new SetEditModeAction(id, value);
    }
}
