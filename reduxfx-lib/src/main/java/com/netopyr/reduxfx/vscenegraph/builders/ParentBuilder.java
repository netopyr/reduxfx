package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ParentBuilder<BUILDER extends ParentBuilder<BUILDER>> extends NodeBuilder<BUILDER> {

    private static final String STYLESHEETS = "stylesheets";

    public ParentBuilder(Class<?> nodeClass,
                         Map<String, VProperty> properties,
                         Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ParentBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public final BUILDER stylesheets(String... value) {
        return property(STYLESHEETS, value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
