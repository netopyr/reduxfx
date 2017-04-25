package com.netopyr.reduxfx.sample.externalstore;

import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

@SuppressWarnings("WeakerAccess")
public class MainView {

    public static VNode view(Integer counter) {
        Objects.requireNonNull(counter, "The parameter 'counter' must not be null");

        return
                StackPane()
                        .padding(50, 100)
                        .children(
                                VBox()
                                        .spacing(20)
                                        .children(
                                                Label()
                                                        .prefWidth(210)
                                                        .text(String.format("You clicked the button %d times", counter)),
                                                Button()
                                                        .text("Click Me!")
                                                        .onAction(e -> Action.INC_COUNTER)
                                        )
                        );
    }
}
