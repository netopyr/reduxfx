package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A {@code SetFilterAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the filter should be
 * changed.
 */
public final class SetFilterAction implements Action {

    private final Filter filter;

    SetFilterAction(Filter filter) {
        this.filter = filter;
    }

    /**
     * The getter of the new {@link Filter} value.
     *
     * @return the new {@code Filter}
     */
    public Filter getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("filter", filter)
                .toString();
    }
}
