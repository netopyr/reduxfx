package com.netopyr.reduxfx.examples.todo.view;

import com.netopyr.reduxfx.examples.todo.state.Filter;
import com.netopyr.reduxfx.examples.todo.actions.Actions;
import com.netopyr.reduxfx.examples.todo.state.AppState;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

import java.util.Objects;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ToggleButton;

/**
 * The class {@code ControlsView} calculates a component consisting of a summary of how many todo-entries are left
 * and a group of {@code ToggleButtons} that can be used to change the {@link Filter}.
 */
class ControlsView {

    private ControlsView() {}

    /**
     * The method {@code ControlsView} calculates a new Controls-component for the given state.
     * <p>
     * This creates a new {@code HBox}, which contains a {@code Label} with the status and an {@code HBox} with a
     * group of {@code ToggleButton}s that can be used to change the {@link Filter}.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created part of the VirtualScenegraph
     * @throws NullPointerException if {@code state} is {@code null}
     */
    static VNode ControlsView(AppState state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        // Calculate the number of active todo-entries
        final int countActive = state.getTodos().count(todoEntry -> !todoEntry.isCompleted());

        return HBox()
                .alignment(Pos.CENTER)
                .spacing(20.0)
                .padding(5.0)
                .children(
                        Label()
                                .id("itemsLeftLabel")
                                // Set the summary of the status
                                .text(String.format("%d %s left",
                                        countActive,
                                        countActive == 1 ? "item" : "items"
                                )),
                        HBox()
                                .minWidth(Region.USE_PREF_SIZE)
                                .minHeight(Region.USE_PREF_SIZE)
                                .maxWidth(Region.USE_PREF_SIZE)
                                .maxHeight(Region.USE_PREF_SIZE)
                                .spacing(10.0)
                                .padding(5.0)
                                .children(
                                        ToggleButton()
                                                .id("showAll")
                                                .text("All")
                                                // The ToggleButton should be selected, if the filter is currently set to Filter.ALL
                                                .selected(state.getFilter() == Filter.ALL)
                                                .toggleGroup("FILTER_BUTTON_GROUP")
                                                // This is how an event-lister is defined. The EventListener get the event and has to return
                                                // the Action that should be dispatched to the Updater.
                                                // If the onAction-event is fired, we want to dispatch a SetFilterAction with the new value.
                                                .onAction(e -> Actions.setFilter(Filter.ALL)),
                                        ToggleButton()
                                                .id("showActive")
                                                .text("Active")
                                                // The ToggleButton should be selected, if the filter is currently set to Filter.ACTIVE
                                                .selected(state.getFilter() == Filter.ACTIVE)
                                                .toggleGroup("FILTER_BUTTON_GROUP")
                                                // If the onAction-event is fired, we want to dispatch a SetFilterAction with the new value.
                                                .onAction(e -> Actions.setFilter(Filter.ACTIVE)),
                                        ToggleButton()
                                                .id("showCompleted")
                                                .text("Completed")
                                                // The ToggleButton should be selected, if the filter is currently set to Filter.COMPLETED
                                                .selected(state.getFilter() == Filter.COMPLETED)
                                                .toggleGroup("FILTER_BUTTON_GROUP")
                                                // If the onAction-event is fired, we want to dispatch a SetFilterAction with the new value.
                                                .onAction(e -> Actions.setFilter(Filter.COMPLETED))
                                )
                );
    }
}
