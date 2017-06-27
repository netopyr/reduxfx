package com.netopyr.reduxfx.examples.screenswitch.view;

import com.netopyr.reduxfx.examples.screenswitch.actions.Actions;
import com.netopyr.reduxfx.examples.screenswitch.state.AppState;
import com.netopyr.reduxfx.examples.screenswitch.state.Screen;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.StackPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stages;

/**
 * The class {@code ViewManager} is responsible for mapping the current state of the application, an instance of
 * {@link AppState}, to the respective VirtualScenegraph, which is then used to update the UI.
 * <p>
 * Every time a new application state becomes available, the method {@link #view(AppState)} is called, which creates
 * a new VirtualScenegraph. A VirtualScenegraph is a data structure that describes the state of the real
 * JavaFX Scenegraph. The ReduxFX runtime analyzes the VirtualScenegraph, calculates the difference between the
 * current VirtualScenegraph and the new VirtualScenegraph and applies the changes. This is done transparently without
 * the developer being aware of it.
 * <p>
 * The advantage of this approach is, that an application developer does not have to worry about the current state of
 * the Scenegraph and eventual state changes. Instead he can simply define a fresh UI with no past. This is a lot
 * simpler than working with a mutable Scenegraph directly.
 * <p>
 * The ReduxFX-API was designed to allow a declarative definition of the VirtualScenegraph. Methods starting with a
 * capital letter create {@code Node}s while methods starting with a small letter define properties and events of the
 * {@code Node}s.
 */
public class ViewManager {

    private ViewManager() {}

    /**
     * The method {@code view} calculates a new main view for the given state.
     * <p>
     * It creates three {@code Stages}, which contain {@code Buttons} to navigate between the {@code Stages}.
     * <p>
     * Please note that we do not have to deal with the old state of the Scenegraph at all. For example we do not
     * have to decide when the Label needs to be updated. We simply set it according to the given state.
     * <p>
     * When one of the {@code Buttons} is clicked, the event handler {@code onAction} returns an instance of
     * {@link com.netopyr.reduxfx.examples.screenswitch.actions.SwitchScreenAction}, which will be passed to the
     * {@link com.netopyr.reduxfx.examples.screenswitch.updater.Updater} to perform the actual change.
     *
     * @param state the current state of the application
     * @return the root {@link VNode} of the created VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    public static VNode view(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return Stages()
                .children(
                        Stage()
                                .title("Left Screen")
                                // This is stage is only visible if state.getScreen() points to the first screen
                                .showing(state.getScreen() == Screen.SCREEN_1)
                                .scene(
                                        Scene()
                                                .root(
                                                        StackPane()
                                                                .padding(50, 100)
                                                                .children(
                                                                        Button()
                                                                                .text("Next")
                                                                                // If the user clicks the button, a SwitchScreenAction
                                                                                // is dispatched to switch to the second screen
                                                                                .onAction(e -> Actions.switchScreen(Screen.SCREEN_2))
                                                                )
                                                )
                                ),
                        Stage()
                                .title("Center Screen")
                                // This is stage is only visible if state.getScreen() points to the second screen
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
                                                                                                // If the user clicks the button, a SwitchScreenAction
                                                                                                // is dispatched to switch to the first screen
                                                                                                .onAction(e -> Actions.switchScreen(Screen.SCREEN_1)),
                                                                                        Button()
                                                                                                .text("Next")
                                                                                                // If the user clicks the button, a SwitchScreenAction
                                                                                                // is dispatched to switch to the third screen
                                                                                                .onAction(e -> Actions.switchScreen(Screen.SCREEN_3))
                                                                                )
                                                                )
                                                )
                                ),
                        Stage()
                                .title("Right Screen")
                                // This is stage is only visible if state.getScreen() points to the third screen
                                .showing(state.getScreen() == Screen.SCREEN_3)
                                .scene(
                                        Scene()
                                                .root(
                                                        StackPane()
                                                                .padding(50, 100)
                                                                .children(
                                                                        Button()
                                                                                .text("Previous")
                                                                                // If the user clicks the button, a SwitchScreenAction
                                                                                // is dispatched to switch to the second screen
                                                                                .onAction(e -> Actions.switchScreen(Screen.SCREEN_2))
                                                                )
                                                )
                                )
                );
    }

}
