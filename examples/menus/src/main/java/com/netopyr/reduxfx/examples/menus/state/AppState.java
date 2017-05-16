package com.netopyr.reduxfx.examples.menus.state;

import com.netopyr.reduxfx.examples.menus.updater.Updater;
import com.netopyr.reduxfx.examples.menus.view.ViewManager;
import javafx.stage.Modality;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An instance of the class {@code AppState} is the root node of the state-tree.
 * <p>
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link Updater}. The {@code Updater} gets the current state together with the action that should be performed
 * and calculates the new state from that.
 * <p>
 * The new state is passed to the {@link ViewManager}-function, which calculates the new virtual Scenegraph.
 */
public final class AppState {

    private final boolean alertVisible;
    private final Modality alertModality;

    private AppState(boolean alertVisible, Modality alertModality) {
        this.alertVisible = alertVisible;
        this.alertModality = alertModality;
    }


    /**
     * The method {@code create} returns a new instance of {@code AppState} with all properties set to their default values.
     * <p>
     * Default values are: {alertVisible: false, alertModality: Modality.APPLICATION_MODAL}
     *
     * @return the new {@code AppState}
     */
    public static AppState create() {
        return new AppState(false, Modality.APPLICATION_MODAL);
    }


    /**
     * This is the getter of the {@code alertVisible}-flag, which is {@code true} if the alert is visible.
     *
     * @return the {@code alertVisible}-flag
     */
    public boolean getAlertVisible() {
        return alertVisible;
    }

    /**
     * The method {@code withAlertVisible} creates a copy of this {@code AppState} with the {@code alertVisible}-flag
     * set to the given value.
     *
     * @param alertVisible the new value of the {@code alertVisible}-flag
     * @return the created {@code AppState}
     */
    public AppState withAlertVisible(boolean alertVisible) {
        return new AppState(alertVisible, this.alertModality);
    }

    /**
     * This is the getter of the {@code alertModality}-flag, which is {@code true} if the alert is modal.
     *
     * @return the {@code alertModality}-flag
     */
    public Modality getAlertModality() {
        return alertModality;
    }

    /**
     * The method {@code withAlertModality} creates a copy of this {@code AppState} with the {@code alertModality}-flag
     * set to the given value.
     *
     * @param alertModality the new value of the {@code alertModality}-flag
     * @return the created {@code AppState}
     */
    public AppState withAlertModality(Modality alertModality) {
        return new AppState(this.alertVisible, alertModality);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("alertVisible", alertVisible)
                .append("alertModality", alertModality)
                .toString();
    }

}
