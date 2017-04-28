package com.netopyr.reduxfx.examples.colorchooser.component.actions;

import com.netopyr.reduxfx.examples.colorchooser.component.updater.ColorChooserUpdater;

/**
 * The {@code ColorChooserAction} interface is a marker interface, which needs to be implemented by all actions of the
 * ColorChooser-Component.
 *
 * {@code ColorChooserAction}s are an implementation of the Command pattern. They describe what should happen within
 * the application, but they do not do any changes themselves. All {@code Action}s are generated in change- and
 * invalidation-listeners, as well as event-handlers, and passed to the
 * {@link ColorChooserUpdater}, which performs the actual change.
 */
public interface ColorChooserAction {
}
