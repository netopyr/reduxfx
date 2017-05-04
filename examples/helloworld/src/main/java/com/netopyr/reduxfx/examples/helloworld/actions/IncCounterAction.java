package com.netopyr.reduxfx.examples.helloworld.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The class {@code IncCounterAction} is the only Action in the HelloWorld-example.
 *
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link com.netopyr.reduxfx.examples.helloworld.updater.Updater},
 * which performs the actual change.
 *
 * In this case {@code IncCounterAction} is created and passed to the {@code Updater} when the user clicks the
 * button.
 */
public final class IncCounterAction {

    IncCounterAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
