package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ParentBuilder<CLASS extends ParentBuilder<CLASS>> extends NodeBuilder<CLASS> {

    private static final String STYLESHEETS = "stylesheets";

    public ParentBuilder(Class<? extends Node> nodeClass,
                         Array<VProperty<?>> properties,
                         Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ParentBuilder<>(nodeClass, properties, eventHandlers);
    }


    public final CLASS stylesheets(String... value) {
        return property(STYLESHEETS, value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
