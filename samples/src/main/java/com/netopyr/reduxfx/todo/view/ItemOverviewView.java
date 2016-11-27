package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.todo.state.TodoEntry;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.layout.Region;

import java.util.Objects;

import static com.netopyr.reduxfx.todo.view.ItemView.ItemView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

class ItemOverviewView {

    static VNode<Action> ItemOverviewView(AppModel state) {
        Objects.requireNonNull(state, "The parameter 'state' must not be null");

        return AnchorPane(
                minWidth(Region.USE_PREF_SIZE),
                minHeight(Region.USE_PREF_SIZE),
                maxWidth(Double.MAX_VALUE),
                maxHeight(Double.MAX_VALUE),
                ListView(
                        TodoEntry.class,
                        id("items"),
                        topAnchor(0.0),
                        rightAnchor(0.0),
                        bottomAnchor(0.0),
                        leftAnchor(0.0),
                        items(state.getTodos()
                                .filter(todoEntry -> {
                                    switch (state.getFilter()) {
                                        case COMPLETED:
                                            return todoEntry.isCompleted();
                                        case ACTIVE:
                                            return !todoEntry.isCompleted();
                                        default:
                                            return true;
                                    }
                                })
                        ),
                        cellFactory(todoEntry -> ItemView((TodoEntry) todoEntry))
                )
        );
    }
}
