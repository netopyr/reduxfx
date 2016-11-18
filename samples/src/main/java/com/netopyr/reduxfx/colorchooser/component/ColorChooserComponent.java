package com.netopyr.reduxfx.colorchooser.component;

import com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserAction;
import com.netopyr.reduxfx.colorchooser.component.actions.ColorChooserActions;
import com.netopyr.reduxfx.colorchooser.component.state.ColorChooserModel;
import com.netopyr.reduxfx.colorchooser.component.updater.ColorChooserUpdater;
import com.netopyr.reduxfx.colorchooser.component.view.ColorChooserView;
import com.netopyr.reduxfx.component.ComponentDriver;
import com.netopyr.reduxfx.vscenegraph.VElement;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.node;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.property;

public class ColorChooserComponent extends VBox {

    @SafeVarargs
    public static <ACTION> VNode<ACTION> ColorChooser(VElement<ACTION>... elements) {
        return node(ColorChooserComponent.class, elements);
    }

    public static <ACTION> VProperty<Color, ACTION> color(Color value, VChangeListener<? super Color, ACTION> listener) {
        return property("color", value, listener);
    }
    public static <ACTION> VProperty<Color, ACTION> color(VChangeListener<? super Color, ACTION> listener) {
        return property("color", listener);
    }



    public ColorChooserComponent() {
        final ColorChooserModel initialData = new ColorChooserModel();
        final ComponentDriver<ColorChooserAction> driver = new ComponentDriver<>(this, initialData, ColorChooserUpdater::update, ColorChooserView::view);

        color = driver.createObjectProperty(this, "color", (oldValue, newValue) -> ColorChooserActions.colorChanged(newValue));

    }



    private final ObjectProperty<Color> color;

    public final Color getColor() {
        return color.get();
    }
    public final void setColor(Color value) {
        color.set(value);
    }
    public final ObjectProperty<Color> colorProperty() {
        return color;
    }
}
