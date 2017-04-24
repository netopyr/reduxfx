package com.netopyr.reduxfx.todo.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.netopyr.reduxfx.todo.updater.Updater;

/**
 * A {@code CompleteAllAction} is passed to the {@link Updater} when all
 * {@code completed}-flags need to be toggled.
 */
public final class CompleteAllAction implements Action {

    CompleteAllAction() {}

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
