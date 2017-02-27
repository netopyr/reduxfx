package com.netopyr.reduxfx.helloworld.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class IncCounterAction {

    IncCounterAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
