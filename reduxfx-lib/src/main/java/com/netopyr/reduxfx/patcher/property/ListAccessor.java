package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.function.Consumer;

public class ListAccessor<ACTION> extends AbstractAccessor<ObservableList, ACTION> {

    public ListAccessor(Class<? extends Node> clazz, VPropertyType propertyType, Consumer<ACTION> dispatcher) {
        super(clazz, propertyType, dispatcher);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(Property<ObservableList> property, ObservableList value) {
        property.getValue().setAll(value);
    }
}
