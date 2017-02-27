package com.netopyr.reduxfx.helloworld.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

public final class AppModel {

    private final int counter;


    private AppModel(int counter) {
        this.counter = counter;
    }

    public static AppModel create() {
        return new AppModel(0);
    }


    public int getCounter() {
        return counter;
    }

    public AppModel withCounter(int newCounter) {
        return new AppModel(newCounter);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("counter", counter)
                .toString();
    }
}
