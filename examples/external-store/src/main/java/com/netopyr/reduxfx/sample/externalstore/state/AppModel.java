package com.netopyr.reduxfx.sample.externalstore.state;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AppModel {

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

    public AppModel withCounter(int counter) {
        return new AppModel(counter);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("counter", counter)
                .toString();
    }
}
