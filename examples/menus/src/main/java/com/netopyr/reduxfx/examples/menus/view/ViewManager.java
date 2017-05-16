package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

public class ViewManager {

    private ViewManager() {
    }

    public static VNode view(AppState state) {
        return Stages()
                .children(
                        Stage()
                                .title("Menu Example")
                                .showing(true)
                                .scene(
                                        Scene()
                                                .root(
                                                        MainView.view(state)
                                                )
                                )
                                .dialogs(
                                        AlertView.view(state)
                                )
                );
    }
}
