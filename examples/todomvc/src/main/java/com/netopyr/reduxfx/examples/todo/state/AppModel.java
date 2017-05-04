package com.netopyr.reduxfx.examples.todo.state;

import com.netopyr.reduxfx.examples.todo.updater.Updater;
import com.netopyr.reduxfx.examples.todo.view.MainView;
import javaslang.collection.Array;
import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Updater}. The {@code Updater} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link MainView}-function, which calculates the new virtual Scenegraph.
 */
public final class AppModel {

    private final String newTodoText;
    private final Seq<TodoEntry> todos;
    private final Filter filter;

    private AppModel(String newTodoText, Seq<TodoEntry> todos, Filter filter) {
        this.newTodoText = newTodoText;
        this.todos = todos;
        this.filter = filter;
    }


    /**
     * The method {@code create} returns a new instance of {@code AppModel} with all properties set to their default values.
     * <p>
     * Default values are: {newTodoText: "", todos: Array.empty(), filter: Filter.ALL}
     *
     * @return the new {@code AppModel}
     */
    public static AppModel create() {
        return new AppModel("", Array.empty(), Filter.ALL);
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
     * The method {@code withNewTodoText} creates a copy of this {@code AppModel} with the {@code newTodoText} set to
     * the given value.
     *
     * @param newNewTodoText the new {@code newNewTodoText}
     * @return the created {@code AppModel}
     * @throws NullPointerException if {@code newNewTodoText} is {@code null}
     */
    public AppModel withNewTodoText(String newNewTodoText) {
        Objects.requireNonNull(newNewTodoText, "The parameter 'newNewTodoText' must not be null");
        return new AppModel(newNewTodoText, this.todos, this.filter);
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
     * The method {@code withTodos} creates a copy of this {@code AppModel} with the {@code todos} set to the given
     * value.
     *
     * @param newTodos the new {@code todos}
     * @return the created {@code AppModel}
     * @throws NullPointerException if {@code newTodos} is {@code null}
     */
    public AppModel withTodos(Seq<TodoEntry> newTodos) {
        Objects.requireNonNull(newTodos, "The parameter 'newTodos' must not be null");
        return new AppModel(this.newTodoText, newTodos, this.filter);
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
     * The method {@code withFilter} creates a copy of this {@code AppModel} with the {@code filter} set to the given
     * value.
     *
     * @param newFilter the new {@code filter}
     * @return the created {@code AppModel}
     * @throws NullPointerException if {@code newFilter} is {@code null}
     */
    public AppModel withFilter(Filter newFilter) {
        Objects.requireNonNull(newFilter, "The parameter 'newFilter' must not be null");
        return new AppModel(this.newTodoText, this.todos, newFilter);
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
