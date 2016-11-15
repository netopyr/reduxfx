package com.netopyr.reduxfx.timer.command;

import com.netopyr.reduxfx.updater.Command;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
        return new ToStringBuilder(this)
                .append("key", key)
                .toString();
    }
}
