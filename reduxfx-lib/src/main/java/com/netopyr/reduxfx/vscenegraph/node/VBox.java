package com.netopyr.reduxfx.vscenegraph.node;

import com.netopyr.reduxfx.vscenegraph.VNode;
import javaslang.collection.Array;

public class VBox<ACTION> extends VNode<ACTION> {

    public static VBox VBox() {
        return new VBox();
    }

    private VBox() {
        super(javafx.scene.layout.VBox.class, Array.empty());
    }
    private VBox(Array<>)

    public VBox spacing(double value) {
        return new VBox();
    }
}
