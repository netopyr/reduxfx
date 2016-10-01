package com.netopyr.reduxfx.patcher;

import javafx.scene.control.ToggleGroup;

import java.util.HashMap;
import java.util.Map;

public class Env {

    private final Map<String, ToggleGroup> toggleGroups = new HashMap<>();

    ToggleGroup getToggleGroup(String id) {
        return toggleGroups.computeIfAbsent(id, key -> new ToggleGroup());
    }
}
