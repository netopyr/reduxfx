package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ContextMenu;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuItem;

/**
 * The class {@code ContextMenuView} is responsible for mapping the current state of the application, an instance of
 * {@link AppState}, to the VirtualScenegraph of a single context menu.
 *
 * For more information about the view-classes, please take a look at {@link ViewManager}.
 */
class ContextMenuView {

    private ContextMenuView() {
    }

    /**
     * The method {@code view} calculates a new context menu for the given state.
     *
     * @param state the current state of the application
     * @return the root {@link VNode} of the created context menu
     * @throws NullPointerException if {@code state} is {@code null}
     */
    static VNode view(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return ContextMenu()
                .items(
                        MenuItem()
                                .disable(state.getAlertVisible())
                                .text("MenuItem 1"),
                        MenuItem()
                                .text("MenuItem 2"),
                        MenuItem()
                                .text("MenuItem 3"),
                        MenuItem()
                                .text("MenuItem 4")
                );
    }

}
