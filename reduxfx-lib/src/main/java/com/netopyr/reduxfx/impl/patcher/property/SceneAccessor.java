package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.impl.patcher.NodeBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.SceneBuilder;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SceneAccessor implements Accessor {

    @Override
    public void set(Consumer<Object> dispatcher, Object node, String name, VProperty vProperty) {
        if (vProperty.isValueDefined()) {
            final Object value = vProperty.getValue();
            if (value instanceof SceneBuilder<?>) {
                final SceneBuilder<?> sceneBuilder = (SceneBuilder<?>) value;
                final Scene scene = new Scene(new Group());
                NodeBuilder.init(dispatcher, scene, sceneBuilder);
                ((Stage) node).setScene(scene);
//                Option<VProperty> sceneProperty = sceneBuilder.getProperties().get("root");
//                if (sceneProperty.isEmpty() || ! sceneProperty.get().isValueDefined()) {
//                    throw new IllegalStateException(String.format("Unable to create Scene %s with empty root", sceneBuilder));
//                }
//                final Option<Object> rootOption = nodeBuilder.create((ReduxFXNode) sceneProperty.get().getValue());
//                if (rootOption.isEmpty()) {
//                    return;
//                }
//
//                if (rootOption.get() instanceof Parent) {
//                    final Parent root = (Parent) rootOption.get();
//                    final Scene scene = new Scene(root);
//                    nodeBuilder.init(scene, sceneBuilder);
//                    nodeBuilder.init
//                    ((Stage) node).setScene(scene);
//                } else {
//                    throw new IllegalStateException(String.format("Unable to convert %s to the root-node", sceneBuilder.getProperties().get("root")));
//                }
            } else {
                throw new IllegalStateException(String.format("Unable to convert the value %s to a Scene", value));
            }

        }
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    protected Object fxToV(Object value) {
//        return getProperties(value).get("vValue");
//    }
//
//    @Override
//    protected Object vToFX(Object value) {
//        if (value instanceof ReduxFXNode) {
//            final Option<Object> nodeOption = nodeBuilder.create((ReduxFXNode) value);
//            if (nodeOption.isEmpty()) {
//                return null;
//            }
//
//            final Object node = nodeOption.get();
//
//            getProperties(node).put("vValue", value);
//            return node;
//        }
//        throw new IllegalStateException(String.format("Unable to convert the value %s to a Node", value));
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    protected void setValue(ReadOnlyProperty property, Object obj) {
//        if (obj instanceof Node) {
//            final Node node = (Node) obj;
//            final Object vObj = fxToV(node);
//            if (vObj instanceof ReduxFXNode) {
//                final ReduxFXNode reduxFXNode = (ReduxFXNode) vObj;
//                ((Property) property).setValue(node);
//                nodeBuilder.init(node, reduxFXNode);
//                return;
//            }
//        }
//        throw new IllegalStateException(String.format("Unable to set the value %s of property %s in class %s",
//                obj, property.getName(), property.getBean().getClass()));
//    }
}
