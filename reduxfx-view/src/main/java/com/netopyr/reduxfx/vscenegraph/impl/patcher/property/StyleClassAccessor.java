package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import io.vavr.collection.Seq;

import java.util.function.Consumer;

public class StyleClassAccessor implements Accessor {

    @SuppressWarnings("unchecked")
    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (! (node instanceof Node)) {
            throw new IllegalStateException("Unable to read value of property styleClass from class " + node.getClass());
        }

        if (vProperty.isValueDefined()) {
            final ObservableList<String> styleClasses = ((Node) node).getStyleClass();
            styleClasses.retainAll(node.getClass().getSimpleName().toLowerCase());

            if (vProperty.getValue() != null) {
                styleClasses.addAll(((Seq) vProperty.getValue()).toJavaList());
            }
        }
    }
}
