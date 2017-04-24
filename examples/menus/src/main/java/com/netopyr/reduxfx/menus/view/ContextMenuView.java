package com.netopyr.reduxfx.menus.view;

import com.netopyr.reduxfx.menus.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ContextMenu;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.MenuItem;

public class ContextMenuView {

    private ContextMenuView() {
    }

    public static VNode view(AppModel state) {
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
