package com.netopyr.reduxfx.colorchooser.component.view;

import com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserAction;
import com.netopyr.reduxfx.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions.updateBlue;
import static com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions.updateGreen;
import static com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions.updateRed;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.*;

public class ColorChooserView {

    private ColorChooserView() {}

    public static VNode<ColorChooserAction> view(ColorChooserModel state) {

        return VBox(
                spacing(10.0),
                Slider(
                        value(state.getRed(), (oldValue, newValue) -> updateRed(newValue)),
                        max(1.0)
                ),
                Slider(
                        value(state.getGreen(), (oldValue, newValue) -> updateGreen(newValue)),
                        max(1.0)
                ),
                Slider(
                        value(state.getBlue(), (oldValue, newValue) -> updateBlue(newValue)),
                        max(1.0)
                )
        );
    }
}
