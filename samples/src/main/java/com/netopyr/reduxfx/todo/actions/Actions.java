package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class Actions {

    private Actions() {}

    public static Action addTodo() {
        return new AddTodo();
    }

    public static Action deleteTodo(int id) {
        return new DeleteTodo(id);
    }

    public static Action editTodo(int id, String text) {
        return new EditTodo(id, text);
    }

    public static Action completeTodo(int id) {
        return new CompleteTodo(id);
    }

    public static Action completeAll() {
        return new CompleteAll();
    }

    public static Action newTextFieldChanged(String text) {
        return new NewTextFieldChanged(text);
    }

    public static Action setFilter(Filter filter) {
        return new SetFilter(filter);
    }

    public static Action setTodoHover(int id, boolean value) {
        return new SetTodoHover(id, value);
    }

    public static Action setEditMode(int id, boolean value) {
        return new SetEditMode(id, value);
    }

    public static final class AddTodo implements Action {

        private AddTodo() {}

        @Override
        public ActionType getType() {
            return ActionType.ADD_TODO;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .toString();
        }
    }

    public static final class CompleteAll implements Action {

        private CompleteAll() {}

        public ActionType getType() {
            return ActionType.COMPLETE_ALL;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .toString();
        }
    }

    public static final class CompleteTodo implements Action {

        private final int id;

        private CompleteTodo(int id) {
            this.id = id;
        }

        @Override
        public ActionType getType() {
            return ActionType.COMPLETE_TODO;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .toString();
        }
    }

    public static final class DeleteTodo implements Action {

        private final int id;

        private DeleteTodo(int id) {
            this.id = id;
        }

        @Override
        public ActionType getType() {
            return ActionType.DELETE_TODO;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .toString();
        }
    }

    public static final class EditTodo implements Action {

        private final int id;
        private final String text;

        private EditTodo(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public ActionType getType() {
            return ActionType.EDIT_TODO;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("text", text)
                    .toString();
        }
    }

    public static class NewTextFieldChanged implements Action {

        private final String text;

        private NewTextFieldChanged(String text) {
            this.text = text;
        }

        @Override
        public ActionType getType() {
            return ActionType.NEW_TEXTFIELD_CHANGED;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("text", text)
                    .toString();
        }
    }

    public static class SetEditMode implements Action {

        private final int id;
        private final boolean value;

        private SetEditMode(int id, boolean value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public ActionType getType() {
            return ActionType.SET_EDIT_MODE;
        }

        public int getId() {
            return id;
        }

        public boolean isValue() {
            return value;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("value", value)
                    .toString();
        }
    }

    public static class SetFilter implements Action {

        private final Filter filter;

        private SetFilter(Filter filter) {
            this.filter = filter;
        }

        @Override
        public ActionType getType() {
            return ActionType.SET_FILTER;
        }

        public Filter getFilter() {
            return filter;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("filter", filter)
                    .toString();
        }
    }

    public static class SetTodoHover implements Action {

        private final int id;
        private final boolean value;

        private SetTodoHover(int id, boolean value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public ActionType getType() {
            return ActionType.SET_TODO_HOVER;
        }

        public int getId() {
            return id;
        }

        public boolean isValue() {
            return value;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("value", value)
                    .toString();
        }
    }
}
