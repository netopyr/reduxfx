package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ParentBuilder<BUILDER extends ParentBuilder<BUILDER>> extends NodeBuilder<BUILDER> {

    private static final String STYLESHEETS = "stylesheets";

    public ParentBuilder(Class<?> nodeClass,
                         Array<VNode> children,
                         Map<String, VProperty> namedChildren,
                         Map<String, VProperty> properties,
                         Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ParentBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
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
