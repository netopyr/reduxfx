package com.netopyr.reduxfx.helloworld.actions;

public class Actions {

    private Actions() {}


    public static IncCounterAction incCounterAction() {
        return new IncCounterAction();
    }
}
