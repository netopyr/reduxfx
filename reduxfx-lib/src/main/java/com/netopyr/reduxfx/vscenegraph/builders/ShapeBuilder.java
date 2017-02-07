package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShapeBuilder<BUILDER extends ShapeBuilder<BUILDER>> extends NodeBuilder<BUILDER> {

    private static final String FILL = "fill";

    public ShapeBuilder(Class<?> nodeClass,
                        Map<String, VProperty> properties,
                        Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ShapeBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER fill(Paint value) {
        return property(FILL, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
