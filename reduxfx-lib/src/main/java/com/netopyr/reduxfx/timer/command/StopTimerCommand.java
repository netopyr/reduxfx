package com.netopyr.reduxfx.timer.command;

import com.netopyr.reduxfx.updater.Command;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StopTimerCommand implements Command {

    private final Object key;

    public StopTimerCommand(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("key", key)
                .toString();
    }
}
