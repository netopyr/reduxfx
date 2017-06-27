package com.netopyr.reduxfx.examples.colorchooser.app.view;

import com.netopyr.reduxfx.examples.colorchooser.app.actions.Actions;
import com.netopyr.reduxfx.examples.colorchooser.app.state.AppState;
import com.netopyr.reduxfx.examples.colorchooser.component.ColorChooserComponent;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

import static com.netopyr.reduxfx.examples.colorchooser.component.ColorChooserComponent.ColorChooser;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Region;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;

/**
 * The class {@code MainView} is responsible for mapping the current state of the application, an instance of
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
public class MainView {

    private MainView() {
    }

    /**
     * The method {@code view} calculates a new main view for the given state.
     * <p>
     * This creates a new {@code Stage} with an {@code HBox}, that contains the {@link ColorChooserComponent} and a
     * {@code Region} which uses the selected {@code Color} as its background color.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    public static VNode view(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return Stage()
                .title("ColorChooser Example")
                .showing(true)
                .scene(
                        Scene()
                                .root(
                                        HBox()
                                                .padding(50.0)
                                                .spacing(20.0)
                                                .children(
                                                        ColorChooser()
                                                                // This is how a property value and a ChangeListener can be set.
                                                                // The ChangeListener gets the old and the new value and has to return the Action that should
                                                                // be dispatched to the Updater.
                                                                // Here we want to set the value of the ColorChooser (first parameter) and fire an
                                                                // UpdateColorAction, if the property of the component changes (second parameter).
                                                                .color(state.getColor(), (oldValue, newValue) -> Actions.updateColor(newValue)),
                                                        Region()
                                                                // The background-color of the region should be set to the color stored in the current state
                                                                .background(state.getColor())
                                                                .minWidth(100.0)
                                                                .minHeight(100.0)
                                                )
                                )
                );
    }
}
