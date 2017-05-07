package com.netopyr.reduxfx.examples.externalstore.view;

import com.netopyr.reduxfx.examples.externalstore.actions.Actions;
import com.netopyr.reduxfx.examples.externalstore.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

@SuppressWarnings("WeakerAccess")
public class MainView {

    public static VNode view(AppModel state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return Stage()
                .title("Example with Redux Java Store")
                .showing(true)
                .scene(
                        Scene()
                                .root(
                                        StackPane()
                                                .padding(50, 100)
                                                .children(
                                                        VBox()
                                                                .spacing(20)
                                                                .children(
                                                                        Label()
                                                                                .prefWidth(210)
                                                                                .text(String.format("You clicked the button %d times", state.getCounter())),
                                                                        Button()
                                                                                .text("Click Me!")
                                                                                .onAction(e -> Actions.incCounterAction())
                                                                )
                                                )
                                )
                );
    }
}
