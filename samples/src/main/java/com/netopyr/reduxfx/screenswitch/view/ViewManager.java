package com.netopyr.reduxfx.screenswitch.view;

import com.netopyr.reduxfx.screenswitch.actions.Actions;
import com.netopyr.reduxfx.screenswitch.state.AppModel;
import com.netopyr.reduxfx.screenswitch.state.Screen;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

public class ViewManager {

    public static VNode view(AppModel state) {
        return Stages(
                Stage()
                        .showing(state.getScreen() == Screen.SCREEN_1)
                        .scene(
                                Scene()
                                        .root(
                                                StackPane()
                                                        .padding(50, 100)
                                                        .children(
                                                                Button()
                                                                        .text("Next")
                                                                        .onAction(e -> Actions.switchScreen(Screen.SCREEN_2))
                                                        )
                                        )
                        ),
                Stage()
                        .showing(state.getScreen() == Screen.SCREEN_2)
                        .scene(
                                Scene()
                                        .root(
                                                StackPane()
                                                        .padding(50, 100)
                                                        .children(
                                                                HBox()
                                                                        .spacing(25)
                                                                        .children(
                                                                                Button()
                                                                                        .text("Previous")
                                                                                        .onAction(e -> Actions.switchScreen(Screen.SCREEN_1)),
                                                                                Button()
                                                                                        .text("Next")
                                                                                        .onAction(e -> Actions.switchScreen(Screen.SCREEN_3))
                                                                        )
                                                        )
                                        )
                        ),
                Stage()
                        .showing(state.getScreen() == Screen.SCREEN_3)
                        .scene(
                                Scene()
                                        .root(
                                                StackPane()
                                                        .padding(50, 100)
                                                        .children(
                                                                Button()
                                                                        .text("Previous")
                                                                        .onAction(e -> Actions.switchScreen(Screen.SCREEN_2))
                                                        )
                                        )
                        )
        );
    }

}
