package com.netopyr.reduxfx.updater;

import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;

public final class Update<S> {

    public static <T> Update<T> of(T state) {
        return new Update<>(state);
    }

    public static <T> Update<T> of(T state, Command... commands) {
        return new Update<>(state, Array.of(commands));
    }

    @SafeVarargs
    public static <T> Update<T> of(T state, Seq<Command>... commands) {
        return new Update<>(state, commands);
    }

    private final S state;
    private final Seq<Command> commands;

    @SafeVarargs
    private Update(S state, Seq<Command>... commands) {
        this.state = state;
        this.commands = Arrays.stream(commands).reduce(Array.empty(), Seq::appendAll);
    }

    public S getState() {
        return state;
    }

    public Seq<Command> getCommands() {
        return commands;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("state", state)
                .append("commands", commands)
                .toString();
    }
}
