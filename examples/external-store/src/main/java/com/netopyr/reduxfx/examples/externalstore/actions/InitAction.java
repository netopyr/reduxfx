package com.netopyr.reduxfx.examples.externalstore.actions;

import com.netopyr.reduxfx.examples.externalstore.reducer.Reducer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An {@code InitAction} is dispatched to the {@link Reducer}
 * to initialize the system.
 */
public class InitAction {

    InitAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
