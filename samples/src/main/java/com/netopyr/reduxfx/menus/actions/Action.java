package com.netopyr.reduxfx.menus.actions;

/**
 * The {@code Action} interface is a marker interface, which needs to be implemented by all actions of the Todo-Application.
 *
 * {@code Action}s are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All {@code Action}s are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link com.netopyr.reduxfx.todo.updater.Updater}, which performs the
 * actual change.
 */
public interface Action {
}
