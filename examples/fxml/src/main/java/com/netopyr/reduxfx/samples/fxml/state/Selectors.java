package com.netopyr.reduxfx.samples.fxml.state;

import java.util.function.Function;

public class Selectors {

    private Selectors() {}


    public static final Function<AppModel, Integer> currentValue = AppModel::getCounter;


}