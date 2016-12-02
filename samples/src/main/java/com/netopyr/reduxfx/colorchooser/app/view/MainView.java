package com.netopyr.reduxfx.colorchooser.app.view;

import com.netopyr.reduxfx.colorchooser.app.actions.Action;
import com.netopyr.reduxfx.colorchooser.app.actions.Actions;
import com.netopyr.reduxfx.colorchooser.app.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent.ColorChooser;
import static com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent.color;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Region;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.background;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minHeight;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minWidth;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.padding;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.spacing;

/**
 * The class {@code MainView} is responsible for mapping the current state of the application, an instance of
 * {@link AppModel}, to the respective VirtualScenegraph, which is then used to update the UI.
 *
 * Every time a new application state becomes available, the method {@link #view(AppModel)} is called and
 * a new VirtualScenegraph created. A VirtualScenegraph is a data structure that describes the state of the real
 * JavaFX Scenegraph. The ReduxFX runtime analyzes the VirtualScenegraph, calculates the difference between the
 * current Scenegraph and the new Scenegraph and applies the changes. This is done transparently without the developer
 * being aware of it.
 *
 * The advantage of this approach is, that the user does not have to worry about the current state of the Scenegraph
 * and eventual state changes, but can simply define a fresh UI with no past. This is a lot simpler than working with
 * a mutable Scenegraph directly.
 *
 * The ReduxFX-API dealing with creating the creation of the VirtualScenegraph was designed to allow a declarative
 * definition of the VirtualScenegraph. Methods starting with a capital letter create {@code Node}s while methods starting
 * with a small letter setup properties of the {@code Node}s.
 */
public class MainView {

    private MainView() {}

    /**
     * The method {@code view} calculates a new main view for the given state.
     *
     * This creates a new {@code HBox}, that contains the
     * {@link com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent} and a {@code Region} which uses
     * the selected {@code Color} as its background color.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created VirtualScenegraph
     */
    public static VNode<Action> view(AppModel state) {

        return HBox(
                padding(50.0),
                spacing(20.0),
                ColorChooser(
                        // This is how a property value and a ChangeListener can be set.
                        // The ChangeListener gets the old and the new value and has to return the Action that should
                        // be dispatched to the Updater.
                        // Here we want to set the value of the ColorChooser (first parameter) and fire an
                        // UpdateColorAction, if the property of the component changes (second parameter).
                        color(state.getColor(), (oldValue, newValue) -> Actions.updateColor(newValue))
                ),
                Region(
                        // The background-color of the region should be set to the color stored in the current state
                        background(state.getColor()),
                        minWidth(100.0),
                        minHeight(100.0)
                )
        );
    }
}
