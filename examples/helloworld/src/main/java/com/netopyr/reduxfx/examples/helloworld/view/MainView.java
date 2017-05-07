package com.netopyr.reduxfx.examples.helloworld.view;

import com.netopyr.reduxfx.examples.helloworld.actions.Actions;
import com.netopyr.reduxfx.examples.helloworld.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

/**
 * The class {@code MainView} is responsible for mapping the current state of the application, an instance of
 * {@link AppModel}, to the respective VirtualScenegraph, which is then used to update the UI.
 * <p>
 * Every time a new application state becomes available, the method {@link #view(AppModel)} is called which creates
 * a new VirtualScenegraph. A VirtualScenegraph is a data structure that describes the state of the real
 * JavaFX Scenegraph. The ReduxFX runtime analyzes the VirtualScenegraph, calculates the difference between the
 * old VirtualScenegraph and the new VirtualScenegraph and applies the changes. This is done transparently without the
 * developer being aware of it.
 * <p>
 * The advantage of this approach is, that the user does not have to worry about the current state of the Scenegraph
 * and eventual state changes. Instead he can simply define a fresh UI with no past. This is a lot simpler than working
 * with a mutable Scenegraph directly.
 * <p>
 * The ReduxFX-API was designed to allow a declarative definition of the VirtualScenegraph. Methods starting with a
 * capital letter create {@code Node}s while methods starting with a small letter setup properties and events of the
 * {@code Node}s.
 */
public class MainView {

    private MainView() {
    }

    /**
     * The method {@code view} calculates a new main view for the given state.
     * <p>
     * It creates a new {@code StackPane}, which contains a {@code VBox} with a {@code Label} and a {@code Button}.
     * The {@code Label} shows how often the button was clicked.
     * <p>
     * Please note that we do not have to deal with the old state of the Scenegraph at all. For example we do not
     * have to decide when the Label needs to be updated. We simply set it according to the given state.
     * <p>
     * When the Button is clicked, the event handler {@code onAction} returns an instance of
     * {@link com.netopyr.reduxfx.examples.helloworld.actions.IncCounterAction}, which will be passed to the
     * {@link com.netopyr.reduxfx.examples.helloworld.updater.Updater} to perform the actual change.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created VirtualScenegraph
     */
    public static VNode view(AppModel state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return Stage()
                .title("HelloWorld - ReduxFX")
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
