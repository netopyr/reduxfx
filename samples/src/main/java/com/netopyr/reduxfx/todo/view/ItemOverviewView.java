package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import java.util.Objects;

/**
 * The class {@code ItemOverviewView} calculates a component with a ListView, that contains all todo-entries.
 */
class ItemOverviewView {

    /**
     * The method {@code ItemOverviewView} calculates a new ItemOverview-component for the given state.
     *
     * This creates a new {@code AnchorPane}, which contains only a {@code ListView}. The {@code ListView} shows
     * all todo-entries.
     *
     * @param state the current state
     * @return the root {@link VNode} of the created part of the VirtualScenegraph
     */
    static VNode ItemOverviewView(AppModel state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");
return null;
//        return AnchorPane(
//                minWidth(Region.USE_PREF_SIZE),
//                minHeight(Region.USE_PREF_SIZE),
//                maxWidth(Double.MAX_VALUE),
//                maxHeight(Double.MAX_VALUE),
//                ListView(
//                        TodoEntry.class,
//                        id("items"),
//                        topAnchor(0.0),
//                        rightAnchor(0.0),
//                        bottomAnchor(0.0),
//                        leftAnchor(0.0),
//                        // With the items property we can set the list of data objects, that should be visualized
//                        // by the ListView. In this case it is the list of the todo-entries, filtered according to
//                        // the current filter setting.
//                        items(state.getTodos()
//                                .filter(todoEntry -> {
//                                    switch (state.getFilter()) {
//                                        case COMPLETED:
//                                            return todoEntry.isCompleted();
//                                        case ACTIVE:
//                                            return !todoEntry.isCompleted();
//                                        default:
//                                            return true;
//                                    }
//                                })
//                        ),
//                        // With the cell factory, we can set define how a single item of the ListView should be
//                        // visualized. It expects an item from the list of items and has to return the root VNode
//                        // of the resulting part of the VirtualScenegraph.
//                        cellFactory(todoEntry -> ItemView((TodoEntry) todoEntry))
//                )
//        );
    }
}
