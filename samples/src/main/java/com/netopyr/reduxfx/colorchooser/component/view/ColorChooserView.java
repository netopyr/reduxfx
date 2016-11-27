package com.netopyr.reduxfx.colorchooser.component.view;

import com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserAction;
import com.netopyr.reduxfx.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.vscenegraph.VNode;

import static com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions.updateBlue;
import static com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions.updateGreen;
import static com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions.updateRed;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Slider;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.max;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.spacing;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.value;

public class ColorChooserView {

    private ColorChooserView() {}

    public static VNode<ColorChooserAction> view(ColorChooserModel state) {

        return VBox(
                spacing(10.0),
                Slider(
                        value(state.getRed(), (oldValue, newValue) -> updateRed(newValue.intValue())),
                        max(255.0)
                ),
                Slider(
                        value(state.getGreen(), (oldValue, newValue) -> updateGreen(newValue.intValue())),
                        max(255.0)
                ),
                Slider(
                        value(state.getBlue(), (oldValue, newValue) -> updateBlue(newValue.intValue())),
                        max(255.0)
                )
        );
    }
}
