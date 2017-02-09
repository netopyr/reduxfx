package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;

public class StageBuilder<BUILDER extends StageBuilder<BUILDER>> extends Builder<BUILDER> {

    private static final String SCENE = "scene";
    private static final String SHOWING = "showing";

    public StageBuilder(
            Class<?> nodeClass,
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
        return (BUILDER) new StageBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER scene(SceneBuilder<?> value) {
        return child(SCENE, value);
    }

    public BUILDER showing(boolean value) {
        return property(SHOWING, value);
    }

}
