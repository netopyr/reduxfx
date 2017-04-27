package com.netopyr.reduxfx.sample.externalstore.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An {@code InitAction} is dispatched to the {@link com.netopyr.reduxfx.sample.externalstore.reducer.Reducer}
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
