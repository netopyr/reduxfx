package com.netopyr.reduxfx.screenswitch.actions;

/**
 * The {@code Action} interface is a marker interface, which needs to be implemented by all actions of the Todo-Application.
 *
 * {@code Action}s are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. All {@code Action}s are generated in change- and invalidation-listeners,
 * as well as event-handlers, and passed to the {@link com.netopyr.reduxfx.todo.updater.Updater}, which performs the
 * actual change.
 *
 * Strictly speaking, deriving all actions from an interface is not needed. But it helps to avoid bugs when defining
 * listeners and event-handlers in the view, because the compiler can check the return type.
 *
 */
public interface Action {
}
