package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.vscenegraph.property.VPropertyType;
import javafx.beans.property.Property;
import javafx.scene.Node;

import java.util.function.Consumer;

public class ValueAccessor<TYPE, ACTION> extends AbstractAccessor<TYPE, ACTION, TYPE> {

    public ValueAccessor(Class<? extends Node> clazz, VPropertyType propertyType, Consumer<ACTION> dispatcher) {
        super(clazz, propertyType, dispatcher);
    }

    @Override
    protected TYPE fxToV(TYPE value) {
        return value;
    }

    @Override
    protected TYPE vToFX(TYPE value) {
        return value;
    }

    @Override
    protected void setValue(Property<TYPE> property, TYPE value) {
        property.setValue(value);
    }
}
