package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import io.vavr.collection.Seq;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.function.Consumer;

public class ListAccessor extends AbstractNoConversionAccessor {

    ListAccessor(MethodHandle methodHandle) {
        super(methodHandle);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setValue(Consumer<Object> dispatcher, ReadOnlyProperty property, Object value) {
        ((ObservableList) property.getValue()).setAll(value == null? Collections.emptyList() : ((Seq) value).toJavaList());
    }
}
