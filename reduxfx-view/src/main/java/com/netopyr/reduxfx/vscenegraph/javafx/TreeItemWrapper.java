package com.netopyr.reduxfx.vscenegraph.javafx;

import javafx.scene.control.TreeItem;

import java.util.HashMap;
import java.util.Map;

public class TreeItemWrapper<T> extends TreeItem<T> {

    private Map<Object, Object> properties;

    public final Map<Object, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<>();
        }
        return properties;
    }
}
