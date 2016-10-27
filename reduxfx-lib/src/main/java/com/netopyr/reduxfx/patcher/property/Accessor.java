package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;

@FunctionalInterface
public interface Accessor<TYPE, ACTION> {

    void set(Node node, VProperty<TYPE, ACTION> vProperty);

}
