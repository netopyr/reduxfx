package com.netopyr.reduxfx.vscenegraph;

import com.netopyr.reduxfx.vscenegraph.node.VBox;
import javaslang.collection.HashMap;

public class VNodeFactory {

    public static <ACTION> VBox<ACTION> VBox() {
        return new VBox<>(javafx.scene.layout.VBox.class, HashMap.empty(), HashMap.empty());
    }

}
