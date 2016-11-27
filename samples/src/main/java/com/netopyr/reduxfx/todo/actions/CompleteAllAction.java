package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@code CompleteAllAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when all
 * {@code completed}-flags need to be toggled.
 */
public final class CompleteAllAction implements Action {

    CompleteAllAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
