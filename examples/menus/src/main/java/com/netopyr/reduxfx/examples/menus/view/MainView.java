package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.actions.Actions;
import com.netopyr.reduxfx.examples.menus.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Menu;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuBar;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuItem;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

/**
 * The class {@code MainView} is responsible for mapping the current state of the application, an instance of
 * {@link AppState}, to the VirtualScenegraph of the main view.
 *
 * For more information about the view-classes, please take a look at {@link ViewManager}.
 */
class MainView {

    private MainView() {
    }

    /**
     * The method {@code view} calculates a new main view for the given state.
     * <p>
     * It creates a new {@code VBox}, which contains a {@code MenuBar} and a {@code Label} with a context menu.
     * <p>
     * Please note that we do not have to deal with the old state of the Scenegraph at all. For example we do not
     * have to decide when the Label needs to be updated. We simply set it according to the given state.
     *
     * @param state the current value of the counter
     * @return the root {@link VNode} of the created VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    static VNode view(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return VBox()
                .children(
                        MenuBar()
                                .menus(
                                        Menu()
                                                .text("View")
                                                .items(
                                                        MenuItem()
                                                                .text("Open Alert")
                                                                // We disable the menu entries if an Alert is already visible
                                                                .disable(state.getAlertVisible())
                                                                // If the user selects this menu-entry, we dispatch an
                                                                // OpenAlert-Action to open a non-modal alert
                                                                .onAction(e -> Actions.openAlert()),
                                                        MenuItem()
                                                                .text("Open Modal Alert")
                                                                // We disable the menu entries if an Alert is already visible
                                                                .disable(state.getAlertVisible())
                                                                // If the user selects this menu-entry, we dispatch an
                                                                // OpenAlert-Action to open a modal alert
                                                                .onAction(e -> Actions.openModalAlert())
                                                )
                                ),
                        StackPane()
                                .padding(50, 100)
                                .children(
                                        Label()
                                                .text("I have a context menu")
                                                .contextMenu(ContextMenuView.view(state))
                                )
                );
    }

}
