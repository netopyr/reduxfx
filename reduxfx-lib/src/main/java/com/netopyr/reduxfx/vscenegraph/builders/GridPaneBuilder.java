package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GridPaneBuilder<BUILDER extends GridPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

    private static final String HGAP = "hgap";
    private static final String VGAP = "vgap";

    public GridPaneBuilder(Class<?> nodeClass,
                           Map<String, VProperty> properties,
                           Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new GridPaneBuilder<>(getNodeClass(), properties, eventHandlers);
    }

    public BUILDER hgap(double value) {
        return property(HGAP, value);
    }

    public BUILDER vgap(double value) {
        return property(VGAP, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
