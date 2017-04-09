package com.netopyr.reduxfx.menus.state;

import javafx.stage.Modality;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An instance of the class {@code AppModel} is the root node of the state-tree.
 *
 * In ReduxFX the whole application state is kept in a single, immutable data structure. This data structure is created
 * in the {@link com.netopyr.reduxfx.todo.updater.Updater}. The {@code Updated} gets the current state together with the
 * {@link com.netopyr.reduxfx.todo.actions.Action} that should be performed and calculates the new state from that.
 *
 * The new state is passed to the {@link com.netopyr.reduxfx.todo.view.MainView}-function, which calculates the new
 * virtual Scenegraph.
 */
public final class AppModel {

    private final boolean alertVisible;
    private final Modality alertModality;

    private AppModel(boolean alertVisible, Modality alertModality) {
        this.alertVisible = alertVisible;
        this.alertModality = alertModality;
    }


    /**
     * The method {@code create} returns a new instance of {@code AppModel} with all properties set to their default values.
     *
     * Default values are: {newTodoText: "", todos: Array.empty(), filter: Filter.ALL}
     */
    public static AppModel create() {
        return new AppModel(false, Modality.APPLICATION_MODAL);
    }


    public boolean getAlertVisible() {
        return alertVisible;
    }

    public AppModel withAlertVisible(boolean alertVisible) {
        return new AppModel(alertVisible, this.alertModality);
    }

    public Modality getAlertModality() {
        return alertModality;
    }

    public AppModel withAlertIsModel(Modality alertModality) {
        return new AppModel(this.alertVisible, alertModality);
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
