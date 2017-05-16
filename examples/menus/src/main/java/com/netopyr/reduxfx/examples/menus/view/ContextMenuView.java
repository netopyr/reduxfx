package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ContextMenu;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuItem;

class ContextMenuView {

    private ContextMenuView() {
    }

    static VNode view(AppState state) {
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
