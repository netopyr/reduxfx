package com.netopyr.reduxfx.colorchooser.app.state;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 *
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link com.netopyr.reduxfx.colorchooser.app.updater.Updater}. The {@code Updater} gets the current state
 * together with the {@link com.netopyr.reduxfx.colorchooser.app.actions.Action} that should be performed and calculates
 * the new state from that.
 *
 * The new state is passed to the {@link com.netopyr.reduxfx.colorchooser.app.view.MainView}-function,
 * which calculates the new virtual Scenegraph.
 */
public final class AppModel {

    private final Color color;

    /**
     * The default constructor creates a new instance of {@code AppModel} with the color set to the default value
     * {@code Color.BLACK}.
     */
    public AppModel() {
        this(Color.BLACK);
    }
    private AppModel(Color color) {
        this.color = color;
    }



    /**
     * This is the getter of the {@code color}.
     *
     * The property {@code color} contains the color that was selected in the
     * {@link com.netopyr.reduxfx.colorchooser.component.ColorChooserComponent}.
     *
     * @return the {@code color}
     */
    public Color getColor() {
        return color;
    }


    /**
     * The method {@code withColor} creates a copy of this {@code AppModel} with the {@code color} set to
     * the given value.
     *
     * @param color the new {@code color}
     * @return the created {@code AppModel}
     * @throws NullPointerException if {@code newNewTodoText} is {@code null}
     */
    public AppModel withColor(Color color) {
        return new AppModel(color);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("color", color)
                .toString();
    }
}
