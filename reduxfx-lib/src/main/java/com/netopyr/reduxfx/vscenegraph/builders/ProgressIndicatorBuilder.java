package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProgressIndicatorBuilder<BUILDER extends ProgressIndicatorBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

    private static final String PROGRESS = "progress";

    public ProgressIndicatorBuilder(Class<?> nodeClass,
                                    Map<String, VProperty> properties,
                                    Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ProgressIndicatorBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER progress(double value) {
        return property(PROGRESS, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
