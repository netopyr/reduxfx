package com.netopyr.reduxfx.menus.view;

import com.netopyr.reduxfx.menus.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

public class ViewManager {

    private ViewManager() {}

    public static VNode view(AppModel state) {
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
