package com.netopyr.reduxfx.examples.todo.state;

import com.netopyr.reduxfx.examples.todo.updater.Updater;
import com.netopyr.reduxfx.examples.todo.view.MainView;
import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An instance of the class {@code AppState} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Updater}. The {@code Updater} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link MainView}-function, which calculates the new virtual Scenegraph.
 */
public final class AppState {

    private final String newTodoText;
    private final Seq<TodoEntry> todos;
    private final Filter filter;

    private AppState(String newTodoText, Seq<TodoEntry> todos, Filter filter) {
        this.newTodoText = Objects.requireNonNull(newTodoText, "The parameter 'newTodoText' must not be null");
        this.todos = Objects.requireNonNull(todos, "The parameter 'todos' must not be null");
        this.filter = Objects.requireNonNull(filter, "The parameter 'filter' must not be null");
    }


    /**
     * The method {@code create} returns a new instance of {@code AppState} with all properties set to their default values.
     * <p>
     * Default values are: {newTodoText: "", todos: Array.empty(), filter: Filter.ALL}
     *
     * @return the new {@code AppState}
     */
    public static AppState create() {
        return new AppState("", Array.empty(), Filter.ALL);
    }


    /**
     * This is the getter of the {@code newTodoText}.
     * <p>
     * If a new todo-entry should be created, this property contains the text that the new todo-entry should contain.
     *
     * @return the {@code newTodoText}
     */
    public String getNewTodoText() {
        return newTodoText;
    }

    /**
     * The method {@code withNewTodoText} creates a copy of this {@code AppState} with the {@code newTodoText} set to
     * the given value.
     *
     * @param newTodoText the new {@code newTodoText}
     * @return the created {@code AppState}
     * @throws NullPointerException if {@code newTodoText} is {@code null}
     */
    public AppState withNewTodoText(String newTodoText) {
        return new AppState(newTodoText, this.todos, this.filter);
    }


    /**
     * This is the getter of the list of {@link TodoEntry}s.
     *
     * @return the list of {@code TodoEntry}s.
     */
    public Seq<TodoEntry> getTodos() {
        return todos;
    }

    /**
     * The method {@code withTodos} creates a copy of this {@code AppState} with the {@code todos} set to the given
     * value.
     *
     * @param todos the new {@code todos}
     * @return the created {@code AppState}
     * @throws NullPointerException if {@code todos} is {@code null}
     */
    public AppState withTodos(Seq<TodoEntry> todos) {
        return new AppState(this.newTodoText, todos, this.filter);
    }


    /**
     * The getter of the currently selected {@link Filter}
     *
     * @return the {@code filter}
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * The method {@code withFilter} creates a copy of this {@code AppState} with the {@code filter} set to the given
     * value.
     *
     * @param filter the new {@code filter}
     * @return the created {@code AppState}
     * @throws NullPointerException if {@code newFilter} is {@code null}
     */
    public AppState withFilter(Filter filter) {
        return new AppState(this.newTodoText, this.todos, filter);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("newTodoText", newTodoText)
                .append("todos", todos)
                .append("filter", filter)
                .toString();
    }
}
