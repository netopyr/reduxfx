package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VBoxBuilder<BUILDER extends VBoxBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String ALIGNMENT = "alignment";
    private static final String SPACING = "spacing";

    public VBoxBuilder(Class<?> nodeClass,
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
        return (BUILDER) new VBoxBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER alignment(Pos value) {
        return property(ALIGNMENT, value);
    }

    public BUILDER spacing(double value) {
        return property(SPACING, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
