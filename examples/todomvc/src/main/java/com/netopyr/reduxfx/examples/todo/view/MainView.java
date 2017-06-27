package com.netopyr.reduxfx.examples.todo.view;

import com.netopyr.reduxfx.examples.todo.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

import java.util.Objects;

import static com.netopyr.reduxfx.examples.todo.view.AddItemView.AddItemView;
import static com.netopyr.reduxfx.examples.todo.view.ItemOverviewView.ItemOverviewView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

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
     * This creates a new {@code VBox}, which contains the title (a {@code Label}), the {@link AddItemView},
     * the {@link ItemOverviewView}, and the {@link ControlsView}. {@code AddItemView} contains the
     * completeAll-{@code Checkbox} and the {@code TextField}. {@code ItemOverviewView} shows the list of todo-entries,
     * and {@code ControlsView} the controls at the bottom of the application.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    public static VNode view(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return Stage()
                .title("TodoMVCFX - ReduxFX")
                .showing(true)
                .scene(
                        Scene()
                                .root(
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
                                                        ControlsView.ControlsView(state)
                                                )
                                )
                );
    }
}
