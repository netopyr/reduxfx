package com.netopyr.reduxfx.todo.actions;

import com.netopyr.reduxfx.todo.state.Filter;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SetFilter implements Action {

    private final Filter filter;

    SetFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public ActionType getType() {
        return ActionType.SET_FILTER;
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
