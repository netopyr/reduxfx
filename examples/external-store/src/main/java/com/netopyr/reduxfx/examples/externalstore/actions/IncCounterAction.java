package com.netopyr.reduxfx.examples.externalstore.actions;

import com.netopyr.reduxfx.examples.externalstore.reducer.Reducer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A {@code IncCounterAction} is passed to the  {@link Reducer} when the user clicks the button.
 * <p>
 * Actions are an implementation of the Command pattern. They describe what should happen within the application,
 * but they do not do any changes themselves. Every time we want to change something in the application-state,
 * we have to generate an Action and pass it to the {@link Reducer},  which performs the actual change.
 */
public final class IncCounterAction {

    IncCounterAction() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
