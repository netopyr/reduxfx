package com.netopyr.reduxfx.todo.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AppModel {

    private final String newToDoText;
    private final List<ToDoEntry> todos;

    @SuppressWarnings("unchecked")
    public AppModel(String newToDoText, Object... elements) {
        this.newToDoText = newToDoText;
        final List<ToDoEntry> result = new ArrayList<>();
        for (final Object element : elements) {
            if (element instanceof ToDoEntry) {
                result.add((ToDoEntry) element);
            } else if (element instanceof Collection) {
                result.addAll((Collection) element);
            } else if (element instanceof ToDoEntry[]) {
                result.addAll(Arrays.asList((ToDoEntry[]) element));
            } else if (element instanceof Stream) {
                result.addAll((List<ToDoEntry>) ((Stream)element).collect(Collectors.toList()));
            } else {
                throw new IllegalArgumentException("Tried to add " + element + " to a ToDoList. Only ToDoEntry, List<ToDoEntry>, and ToDoEntry[] are allowed.");
            }
        }
        this.todos = Collections.unmodifiableList(result);
    }

    public String getNewToDoText() {
        return newToDoText;
    }

    public List<ToDoEntry> getTodos() {
        return todos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ToDoList{");
        sb.append("newToDoText='").append(newToDoText).append('\'');
        sb.append(", todos=").append(todos);
        sb.append('}');
        return sb.toString();
    }
}
