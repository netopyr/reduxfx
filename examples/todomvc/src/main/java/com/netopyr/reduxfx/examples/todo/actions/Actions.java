package com.netopyr.reduxfx.examples.todo.actions;

import com.netopyr.reduxfx.examples.todo.state.Filter;
import com.netopyr.reduxfx.examples.todo.state.TodoEntry;
import com.netopyr.reduxfx.examples.todo.updater.Updater;

/**
 * The class {@code Actions} contains factory-methods for all actions that are available in this application.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All actions are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link Updater}, which performs the actual change.
 */
public final class Actions {

    // AddTodoAction and CompleteAllAction are stateless, therefore only a single instance can be reused
    private static final AddTodoAction ADD_TODO_ACTION = new AddTodoAction();
    private static final CompleteAllAction COMPLETE_ALL_ACTION = new CompleteAllAction();

    private Actions() {
    }


    /**
     * This method generates an {@link AddTodoAction}.
     * <p>
     * This action is passed to the {@link Updater} when a new {@link TodoEntry} should be created and added to the list.
     *
     * @return the {@code AddTodoAction}
     */
    public static AddTodoAction addTodo() {
        return ADD_TODO_ACTION;
    }


    /**
     * This method generates a {@link DeleteTodoAction}.
     * <p>
     * This action is passed to the {@link Updater} when a {@link TodoEntry} should be deleted.
     *
     * @param id the {@code id} of the {@code TodoEntry} that needs to be deleted
     * @return the {@code DeleteTodoAction}
     */
    public static DeleteTodoAction deleteTodo(int id) {
        return new DeleteTodoAction(id);
    }


    /**
     * This method generates a {@link EditTodoAction}.
     * <p>
     * This action is passed to the {@link Updater} when the {@code text} of a {@link TodoEntry} needs to be changed
     *
     * @param id   the {@code id} of the {@code TodoEntry} which {@code text} needs to be changed
     * @param text the new {@code text}
     * @return the {@code EditTodoAction}
     * @throws NullPointerException if {@code text} is {@code null}
     */
    public static EditTodoAction editTodo(int id, String text) {
        return new EditTodoAction(id, text);
    }


    /**
     * This method generates a {@link CompleteTodoAction}.
     * <p>
     * This action is passed to the {@link Updater} when the {@code completed}-flag of a {@link TodoEntry} needs to be toggled.
     *
     * @param id the {@code id} of the {@code TodoEntry} which {@code completed}-flag needs to be toggled
     * @return the {@code CompleteTodoAction}
     */
    public static CompleteTodoAction completeTodo(int id) {
        return new CompleteTodoAction(id);
    }


    /**
     * This method generates a {@link CompleteAllAction}.
     * <p>
     * This action is passed to the {@link Updater} when all {@code completed}-flags need to be toggled.
     *
     * @return the {@code CompleteAllAction}
     */
    public static CompleteAllAction completeAll() {
        return COMPLETE_ALL_ACTION;
    }


    /**
     * This method generates a {@link NewTextFieldChangedAction}.
     * <p>
     * This action is passed to the {@link Updater} when the value of the {@code TextField} for new {@link TodoEntry}s
     * has changed.
     *
     * @param text the new value of the {@code TextField}
     * @return the {@code NewTextFieldChangedAction}
     * @throws NullPointerException if {@code text} is {@code null}
     */
    public static NewTextFieldChangedAction newTextFieldChanged(String text) {
        return new NewTextFieldChangedAction(text);
    }


    /**
     * This method generates a {@link SetFilterAction}.
     * <p>
     * This action is passed to the {@link Updater} when the filter should be changed.
     *
     * @param filter the new {@link Filter}-value
     * @return the {@code SetFilterAction}
     * @throws NullPointerException if {@code filter} is {@code null}
     */
    public static SetFilterAction setFilter(Filter filter) {
        return new SetFilterAction(filter);
    }


    /**
     * This method generates a {@link SetTodoHoverAction}.
     * <p>
     * This action is passed to the {@link Updater} when the {@code hover}-flag of a {@link TodoEntry} should be changed.
     * The {@code hover}-flag signals, if the mouse hovers over the item.
     *
     * @param id    the {@code id} of the {@code TodoEntry} which {@code hover}-flag should be changed
     * @param value the new value of the {@code hover}-flag
     * @return the {@code SetTodoHoverAction}
     */
    public static SetTodoHoverAction setTodoHover(int id, boolean value) {
        return new SetTodoHoverAction(id, value);
    }


    /**
     * This method generates a {@link SetEditModeAction}.
     * <p>
     * This action is passed to the {@link Updater} when the {@code edit}-flag of a {@link TodoEntry} should be changed.
     * The {@code edit}-flag signals, if the item is in editing- or readonly-mode.
     *
     * @param id    the {@code id} of the {@code TodoEntry} which {@code edit}-flag should be changed
     * @param value the new value of the {@code edit}-flag
     * @return the {@code SetEditModeAction}
     */
    public static SetEditModeAction setEditMode(int id, boolean value) {
        return new SetEditModeAction(id, value);
    }
}
