package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Map;

public class StageBuilder<BUILDER extends StageBuilder<BUILDER>> extends Builder<BUILDER> {

    private static final String SCENE = "scene";
    private static final String SHOWING = "showing";

    public StageBuilder(Class<?> nodeClass, Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new StageBuilder<>(getNodeClass(), properties, eventHandlers);
    }


    public BUILDER scene(SceneBuilder<?> value) {
        return property(SCENE, value);
    }

    public BUILDER showing(boolean value) {
        return property(SHOWING, value);
    }

}
