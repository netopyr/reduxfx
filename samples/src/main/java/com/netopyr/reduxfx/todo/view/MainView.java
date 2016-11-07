package com.netopyr.reduxfx.todo.view;

import com.netopyr.reduxfx.todo.actions.Action;
import com.netopyr.reduxfx.todo.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.todo.view.AddItemView.AddItemView;
import static com.netopyr.reduxfx.todo.view.ControlsView.ControlsView;
import static com.netopyr.reduxfx.todo.view.ItemOverviewView.ItemOverviewView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.alignment;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.id;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.maxHeight;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.maxWidth;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minHeight;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minWidth;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.stylesheets;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.text;

public class MainView {

    private MainView() {}

    public static VNode<Action> view(AppModel state) {

        return
                VBox(
                        alignment(Pos.CENTER),
                        minWidth(Region.USE_PREF_SIZE),
                        minHeight(Region.USE_PREF_SIZE),
                        maxWidth(Double.MAX_VALUE),
                        maxHeight(Double.MAX_VALUE),
                        stylesheets(MainView.class.getResource("main.css").toString()),

                        Label(
                                id("title"),
                                text("todos")
                        ),

                        AddItemView(state),
                        ItemOverviewView(state),
                        ControlsView(state)
                );
    }
}
