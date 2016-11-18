package com.netopyr.reduxfx.colorchooser.app.view;

import com.netopyr.reduxfx.colorchooser.app.actions.Action;
import com.netopyr.reduxfx.colorchooser.app.actions.Actions;
import com.netopyr.reduxfx.colorchooser.app.state.AppModel;
import com.netopyr.reduxfx.vscenegraph.VNode;
import javafx.scene.paint.Color;

import static com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent.ColorChooser;
import static com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent.color;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Region;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.background;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minHeight;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.minWidth;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.padding;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.spacing;

public class MainView {

    private MainView() {}

    public static VNode<Action> view(AppModel state) {

        return HBox(
                padding(50.0),
                spacing(20.0),
                ColorChooser(
                        color(Color.VIOLET, (oldValue, newValue) -> Actions.updateColor(newValue))
                ),
                Region(
                        background(state.getColor()),
                        minWidth(100.0),
                        minHeight(100.0)
                )
        );
    }
}
