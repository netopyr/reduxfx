package com.netopyr.reduxfx.patcher;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javaslang.control.Option;

class NodeUtilities {

    private NodeUtilities() {}

    static Option<java.util.List<Node>> getChildren(Node node) {
        return node instanceof Group ? Option.of(((Group) node).getChildren())
                : node instanceof Pane ? Option.of(((Pane) node).getChildren())
                : Option.none();
    }
}
