package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * A {@code SetFilterAction} is passed to the {@link com.netopyr.reduxfx.todo.updater.Updater} when the filter should be
 * changed.
 */
public final class SetFilterAction implements Action {

    private final Filter filter;

    SetFilterAction(Filter filter) {
        Objects.requireNonNull(filter, "The parameter 'filter' must not be null");
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("filter", filter)
                .toString();
    }
}
