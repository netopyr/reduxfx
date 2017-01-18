package com.netopyr.reduxfx.timer.command;

import com.netopyr.reduxfx.updater.Command;
import javafx.util.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.function.Function;

public class StartTimerCommand implements Command {

    private final Object key;
    private final Duration delay;
    private final Duration interval;
    private final Function<Long, Object> mapper;

    public StartTimerCommand(Object key, Duration delay, Duration interval, Function<Long, Object> mapper) {
        this.key = key;
        this.delay = delay;
        this.interval = interval;
        this.mapper = mapper;
    }

    public Object getKey() {
        return key;
    }

    public Duration getDelay() {
        return delay;
    }

    public Duration getInterval() {
        return interval;
    }

    public Function<Long, Object> getMapper() {
        return mapper;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("key", key)
                .append("delay", delay)
                .append("interval", interval)
                .append("mapper", mapper)
                .toString();
    }
}
