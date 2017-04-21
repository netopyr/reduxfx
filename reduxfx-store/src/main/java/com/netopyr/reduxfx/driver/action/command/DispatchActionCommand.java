package com.netopyr.reduxfx.driver.action.command;

import com.netopyr.reduxfx.updater.Command;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DispatchActionCommand implements Command {

    final Object action;

    public DispatchActionCommand(Object action) {
        this.action = action;
    }

    public Object getAction() {
        return action;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("action", action)
                .toString();
    }
}
