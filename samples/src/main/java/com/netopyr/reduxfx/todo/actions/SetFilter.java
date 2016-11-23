package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class SetFilter implements Action {

    private final Filter filter;

    SetFilter(Filter filter) {
        this.filter = filter;
    }

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
