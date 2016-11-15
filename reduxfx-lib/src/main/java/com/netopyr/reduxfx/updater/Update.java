package com.netopyr.reduxfx.updater;

import javaslang.collection.Array;
import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class Update<STATE> {

    public static <T> Update<T> of(T state, Command... commands) {
        return Update.of(state, Array.of(commands));
    }

    public static <T> Update<T> of(T state, Seq<? extends Command> commands) {
        return new Update<>(state, commands);
    }

    private final STATE state;
    private final Seq<? extends Command> commands;

    private Update(STATE state, Seq<? extends Command> commands) {
        this.state = state;
        this.commands = commands;
    }

    public STATE getState() {
        return state;
    }

    public Seq<? extends Command> getCommands() {
        return commands;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("state", state)
                .append("commands", commands)
                .toString();
    }
}
