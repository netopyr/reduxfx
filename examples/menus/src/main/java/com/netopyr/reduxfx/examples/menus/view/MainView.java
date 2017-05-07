package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.actions.Actions;
import com.netopyr.reduxfx.examples.menus.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Menu;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuBar;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuItem;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

class MainView {

    private MainView() {
    }

    static VNode view(AppModel state) {
        return VBox()
                .children(
                        MenuBar()
                                .menus(
                                        Menu()
                                                .text("View")
                                                .items(
                                                        MenuItem()
                                                                .text("Open Alert")
                                                                .disable(state.getAlertVisible())
                                                                .onAction(e -> Actions.openAlert()),
                                                        MenuItem()
                                                                .text("Open Modal Alert")
                                                                .disable(state.getAlertVisible())
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
