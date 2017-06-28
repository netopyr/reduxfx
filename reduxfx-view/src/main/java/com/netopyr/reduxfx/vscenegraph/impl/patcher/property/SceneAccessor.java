package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.SceneBuilder;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeBuilder;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import io.vavr.control.Option;

import java.util.function.Consumer;

public class SceneAccessor implements NodeAccessor {

    @Override
    public void set(Consumer<Object> dispatcher, Object parent, String name, Option<VNode> vNode) {
        if (vNode.isDefined()) {
            final VNode value = vNode.get();
            if (value instanceof SceneBuilder<?>) {
                final SceneBuilder<?> sceneBuilder = (SceneBuilder<?>) value;
                final Scene scene = new Scene(new Group());
                NodeBuilder.init(dispatcher, scene, sceneBuilder);
                ((Stage) parent).setScene(scene);
            } else {
                throw new IllegalStateException(String.format("Unable to convert the value %s to a Scene", value));
            }
        }
    }
}
