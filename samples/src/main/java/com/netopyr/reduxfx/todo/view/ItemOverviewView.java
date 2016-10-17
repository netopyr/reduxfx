package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.ToDoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.todo.view.ItemView.ItemView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

class ItemOverviewView {

    static VNode<Action> ItemOverviewView(AppModel state) {
        return AnchorPane(
                minWidth(Region.USE_PREF_SIZE),
                minHeight(Region.USE_PREF_SIZE),
                maxWidth(Double.MAX_VALUE),
                maxHeight(Double.MAX_VALUE),
                ListView(
                        ToDoEntry.class,
                        id("items"),
                        topAnchor(0.0),
                        rightAnchor(0.0),
                        bottomAnchor(0.0),
                        leftAnchor(0.0),
                        items(state.getTodos()
                                .filter(toDoEntry -> {
                                    switch (state.getFilter()) {
                                        case COMPLETED:
                                            return toDoEntry.isCompleted();
                                        case ACTIVE:
                                            return !toDoEntry.isCompleted();
                                        default:
                                            return true;
                                    }
                                })
                        ),
                        cellFactory(toDoEntry -> ItemView((ToDoEntry) toDoEntry))
                )
        );
    }
}
