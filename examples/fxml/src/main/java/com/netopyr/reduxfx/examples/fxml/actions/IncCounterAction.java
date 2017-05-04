package com.netopyr.reduxfx.examples.fxml.actions;

import com.netopyr.reduxfx.examples.fxml.updater.Updater;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The class {@code IncCounterAction} is the only Action in the HelloWorld-example.
 *
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Updater}, which performs the actual change.
 *
 * In this case {@code IncCounterAction} is created and passed to the {@code Updater} when the user clicks the
 * button.
 */
public final class IncCounterAction {

    IncCounterAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
