package com.netopyr.reduxfx.examples.fxml.state;

import java.util.function.Function;

public class Selectors {

    private Selectors() {}


    public static final Function<AppModel, Integer> currentValue = AppModel::getCounter;


}