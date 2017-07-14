package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.SceneAccessor;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.WindowsAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.stage.WindowEvent;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.CLOSE_REQUEST;

@SuppressWarnings("unused")
public class WindowBuilder<B extends WindowBuilder<B>> extends Builder<B> {

    private static final String DIALOGS = "dialogs";
    private static final String SCENE = "scene";
    private static final String SHOWING = "showing";
    private static final String WINDOWS = "windows";

    public WindowBuilder(Class<?> nodeClass,
                         Map<String, Array<VNode>> childrenMap,
                         Map<String, Option<VNode>> singleChildMap,
                         Map<String, VProperty> properties,
                         Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new WindowBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B dialogs(VNode... nodes) {
        Accessors.registerNodeListAccessor(getNodeClass(), DIALOGS, WindowsAccessor::new);
        return children(DIALOGS, nodes == null? Array.empty() : Array.of(nodes));
    }

    public B scene(SceneBuilder<?> value) {
        Accessors.registerNodeAccessor(getNodeClass(), SCENE, SceneAccessor::new);
        return child(SCENE, value);
    }

    public B windows(VNode... nodes) {
        Accessors.registerNodeListAccessor(getNodeClass(), WINDOWS, WindowsAccessor::new);
        return children(WINDOWS, nodes == null? Array.empty() : Array.of(nodes));
    }

    public B showing(boolean value) {
        return property(value? VProperty.Phase.SHOW_STAGE : VProperty.Phase.HIDE_STAGE, SHOWING, value);
    }


    public B onCloseRequest(VEventHandler<WindowEvent> eventHandler) {
        return onEvent(CLOSE_REQUEST, eventHandler);
    }

}
