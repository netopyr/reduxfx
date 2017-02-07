package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

import java.util.Objects;

import static com.netopyr.reduxfx.todo.view.AddItemView.AddItemView;
import static com.netopyr.reduxfx.todo.view.ControlsView.ControlsView;
import static com.netopyr.reduxfx.todo.view.ItemOverviewView.ItemOverviewView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

/**
 * The class {@code MainView} is responsible for mapping the current state of the application, an instance of
 * {@link AppModel}, to the respective VirtualScenegraph, which is then used to update the UI.
 * <p>
 * Every time a new application state becomes available, the method {@link MainView#view(AppModel)} is called and
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
public class MainView {

    private MainView() {
    }

    /**
     * The method {@code view} calculates a new main view for the given state.
     * <p>
     * This creates a new {@code VBox}, which contains the title (a {@code Label}), the {@link AddItemView},
     * the {@link ItemOverviewView}, and the {@link ControlsView}. {@code AddItemView} contains the
     * completeAll-{@code Checkbox} and the {@code TextField}. {@code ItemOverviewView} shows the list of todo-entries,
     * and {@code ControlsView} the controls at the bottom of the application.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created VirtualScenegraph
     */
    public static VNode view(AppModel state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return
                VBox()
                        .alignment(Pos.CENTER)
                        .minWidth(Region.USE_PREF_SIZE)
                        .minHeight(Region.USE_PREF_SIZE)
                        .maxWidth(Double.MAX_VALUE)
                        .maxHeight(Double.MAX_VALUE)
                        .stylesheets(MainView.class.getResource("main.css").toString())

                        .children(
                                Label()
                                        .id("title")
                                        .text("todos"),
                                AddItemView(state),
                                ItemOverviewView(state),
                                ControlsView(state)
                        );
    }
}
