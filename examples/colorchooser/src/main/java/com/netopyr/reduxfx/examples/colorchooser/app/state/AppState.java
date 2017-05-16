package com.netopyr.reduxfx.examples.colorchooser.app.state;

import com.netopyr.reduxfx.examples.colorchooser.app.updater.Updater;
import com.netopyr.reduxfx.examples.colorchooser.app.view.MainView;
import com.netopyr.reduxfx.examples.colorchooser.component.ColorChooserComponent;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * An instance of the class {@code AppState} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Updater}. The {@code Updater} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link MainView}-function, which calculates the new virtual Scenegraph.
 */
public final class AppState {

    private final Color color;

    private AppState(Color color) {
        this.color = Objects.requireNonNull(color, "The parameter 'color' must not be null");
    }


    /**
     * The method {@code create} returns a new instance of {@code AppState} with the color set to the default value
     * {@code Color.BLACK}.
     *
     * @return the new {@code AppState}
     */
    public static AppState create() {
        return new AppState(Color.BLACK);
    }


    /**
     * This is the getter of the {@code color}.
     * <p>
     * The property {@code color} contains the color that was selected in the {@link ColorChooserComponent}.
     *
     * @return the {@code color}
     */
    public Color getColor() {
        return color;
    }


    /**
     * The method {@code withColor} creates a copy of this {@code AppState} with the {@code color} set to
     * the given value.
     *
     * @param color the new {@code color}
     * @return the created {@code AppState}
     * @throws NullPointerException if {@code newNewTodoText} is {@code null}
     */
    public AppState withColor(Color color) {
        return new AppState(color);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("color", color)
                .toString();
    }
}
