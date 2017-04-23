package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import javafx.beans.property.ReadOnlyProperty;
import javafx.scene.Node;

import java.util.function.Consumer;

public class FocusedAccessor extends AbstractNoConversionAccessor {

    public FocusedAccessor() {
        super(NodeUtilities.getPropertyGetter(Node.class, "focused").get());
    }

    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        if (Boolean.TRUE.equals(value)) {
            ((Node) property.getBean()).requestFocus();
        }
    }
}
