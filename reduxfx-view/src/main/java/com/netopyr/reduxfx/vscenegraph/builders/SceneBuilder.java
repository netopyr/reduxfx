package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SceneBuilder<B extends SceneBuilder<B>> extends Builder<B> {

    private static final String ROOT = "root";

    public SceneBuilder(Class<?> nodeClass,
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
        return (B) new SceneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }

    @Override
    public Option<Object> produce() {
        return Scene.class.equals(getNodeClass())? Option.of(new Scene(new Group())) : super.produce();
    }


    public B root(VNode value) {
        return child(ROOT, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
