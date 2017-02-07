package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class HBoxBuilder<BUILDER extends HBoxBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String SPACING = "spacing";
    private static final String ALIGNMENT = "alignment";

    public HBoxBuilder(Class<?> nodeClass,
                       Map<String, VProperty> properties,
                       Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new HBoxBuilder<>(getNodeClass(), properties, eventHandlers);
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
