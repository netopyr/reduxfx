package com.netopyr.reduxfx.colorchooser.component.view;

import com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserAction;
import com.netopyr.reduxfx.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.VScenegraphFactory;
import com.netopyr.reduxfx.vscenegraph.node.VBoxBuilder;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

/**
 * The class {@code ColorChooserView} is responsible for mapping the current state of the component, an instance of
 * {@link ColorChooserModel}, to the respective VirtualScenegraph, which is then used to update the UI.
 * <p>
 * Every time a new application state becomes available, the method {@link #view(ColorChooserModel)} is called and
 * a new VirtualScenegraph created. A VirtualScenegraph is a data structure that describes the state of the real
 * JavaFX Scenegraph. The ReduxFX runtime analyzes the VirtualScenegraph, calculates the difference between the
 * current Scenegraph and the new Scenegraph and applies the changes. This is done transparently without the developer
 * being aware of it.
 * <p>
 * The advantage of this approach is, that the user does not have to worry about the current state of the Scenegraph
 * and eventual state changes, but can simply define a fresh UI with no past. This is a lot simpler than working with
 * a mutable Scenegraph directly.
 * <p>
 * The ReduxFX-API dealing with creating the creation of the VirtualScenegraph was designed to allow a declarative
 * definition of the VirtualScenegraph. Methods starting with a capital letter create {@code Node}s while methods starting
 * with a small letter setup properties of the {@code Node}s.
 */
public class ColorChooserView {

    private ColorChooserView() {
    }

    /**
     * The method {@code view} calculates a new view for the given state.
     * <p>
     * This creates a new {@code VBox}, that contains three {@code Slider}s, each representing one of the color values.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created VirtualScenegraph
     */
    public static VNode<ColorChooserAction> view(ColorChooserModel state) {

        return (VNode<ColorChooserAction>) VBox()
                .spacing(10.0)
//                .children(
//                        Slider()
//                                // This is how a property value and a ChangeListener can be set.
//                                // The ChangeListener gets the old and the new value and has to return the Action that should
//                                // be dispatched to the Updater.
//                                // Here we want to set the value of the red color value (first parameter) and fire an
//                                // UpdateRedAction, if the property of the component changes (second parameter).
//                                .value(state.getRed(), (oldValue, newValue) -> updateRed(newValue.intValue()))
//                                .max(255.0),
//                        Slider()
//                                // We set the value of the Slider to the value that is stored in the state and
//                                // we create an updateGreenAction to change the respective property in the state.
//                                .value(state.getGreen(), (oldValue, newValue) -> updateGreen(newValue.intValue()))
//                                .max(255.0),
//                        Slider()
//                                // We set the value of the Slider to the value that is stored in the state and
//                                // we create an updateBluenAction to change the respective property in the state.
//                                .value(state.getBlue(), (oldValue, newValue) -> updateBlue(newValue.intValue()))
//                                .max(255.0)
//                );
        ;
    }
}
