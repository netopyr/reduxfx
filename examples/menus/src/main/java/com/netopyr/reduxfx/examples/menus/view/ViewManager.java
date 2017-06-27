package com.netopyr.reduxfx.examples.menus.view;

import com.netopyr.reduxfx.examples.menus.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
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

    private ViewManager() {
    }

    /**
     * The method {@code view} calculates a new main view for the given state.
     * <p>
     * It creates a new {@code Stage}, which contains the content defined in {@link MainView} and a single
     * {@code Alert}, which is defined in {@link AlertView}.
     * <p>
     * Please note that we do not have to deal with the old state of the Scenegraph at all. For example we do not
     * have to decide when the Label needs to be updated. We simply set it according to the given state.
     *
     * @param state the current state of the application
     * @return the root {@link VNode} of the created VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    public static VNode view(AppState state) {
        return Stages()
                .children(
                        Stage()
                                .title("Menu Example")
                                .showing(true)
                                .scene(
                                        Scene()
                                                .root(
                                                        MainView.view(state)
                                                )
                                )
                                .dialogs(
                                        AlertView.view(state)
                                )
                );
    }
}
