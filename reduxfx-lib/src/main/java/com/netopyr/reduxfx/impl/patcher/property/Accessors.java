package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.impl.patcher.NodeBuilder;
import com.netopyr.reduxfx.impl.patcher.NodeUtilities;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.Consumer;

public class Accessors {

    private final Consumer<Object> dispatcher;

    private Map<Tuple2<Class<?>, String>, Accessor> accessorMap = HashMap.empty();
    private Map<Tuple2<Class<?>, String>, Accessor> layoutAccessorMap = HashMap.empty();

    public Accessors(Consumer<Object> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void init(NodeBuilder nodeBuilder) {
        registerAccessor(Tuple.of(ToggleButton.class, "toggleGroup"), new ToggleGroupAccessor(NodeUtilities.getPropertyGetter(ToggleButton.class, "toggleGroup").get(), dispatcher));
        registerAccessor(Tuple.of(Labeled.class, "graphic"), new NodeAccessor(NodeUtilities.getPropertyGetter(Labeled.class, "graphic").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "top"), new NodeAccessor(NodeUtilities.getPropertyGetter(BorderPane.class, "top").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "right"), new NodeAccessor(NodeUtilities.getPropertyGetter(BorderPane.class, "right").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "bottom"), new NodeAccessor(NodeUtilities.getPropertyGetter(BorderPane.class, "bottom").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "left"), new NodeAccessor(NodeUtilities.getPropertyGetter(BorderPane.class, "left").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "center"), new NodeAccessor(NodeUtilities.getPropertyGetter(BorderPane.class, "center").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(Node.class, "focused"), new FocusedAccessor(NodeUtilities.getPropertyGetter(Node.class, "focused").get(), dispatcher));
        registerAccessor(Tuple.of(Stage.class, "scene"), new SceneAccessor(nodeBuilder));
        registerAccessor(Tuple.of(Scene.class, "root"), new NodeAccessor(NodeUtilities.getPropertyGetter(Scene.class, "root").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(Stage.class, "showing"), new StageShowingAccessor(NodeUtilities.getPropertyGetter(Stage.class, "showing").get(), dispatcher));
    }

    public void registerAccessor(Tuple2<Class<?>, String> propertyKey, Accessor accessor) {
        cacheAccessor(propertyKey, accessor);
    }

    public Option<Accessor> getAccessor(Object node, String propertyName) {
        final Class<?> nodeClass = node.getClass();
        final Tuple2<Class<?>, String> propertyKey = Tuple.of(nodeClass, propertyName);

        return accessorMap.get(propertyKey)
                .orElse(() -> searchInCache(accessorMap, propertyKey)
                        .orElse(() -> createAccessor(nodeClass, propertyName))
                        .map(accessor -> cacheAccessor(propertyKey, accessor))
                )
                .orElse(() -> {
                    if (! (node instanceof Node) || ((Node) node).getParent() == null) {
                        return Option.none();
                    }
                    final Class<? extends Parent> parentClass = ((Node) node).getParent().getClass();
                    final Tuple2<Class<?>, String> layoutKey = Tuple.of(parentClass, propertyName);
                    return Pane.class.isAssignableFrom(parentClass) ?
                            layoutAccessorMap.get(layoutKey)
                                    .orElse(() -> searchInCache(layoutAccessorMap, layoutKey)
                                            .orElse(() -> createLayoutAccessor(parentClass, propertyName))
                                            .map(accessor -> cacheLayoutAccessor(layoutKey, accessor))
                                    )
                            : Option.none();
                });
    }

    private static Option<Accessor> searchInCache(Map<Tuple2<Class<?>, String>, Accessor> map, Tuple2<Class<?>, String> propertyKey) {
        final String propertyName = propertyKey._2;
        for (Class<?> clazz = propertyKey._1.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            final Option<Accessor> accessor = map.get(Tuple.of(clazz, propertyName));
            if (accessor.isDefined()) {
                return accessor;
            }
        }
        return Option.none();
    }

    private Accessor cacheAccessor(Tuple2<Class<?>, String> propertyKey, Accessor accessor) {
        accessorMap = doCacheAccessor(accessorMap, propertyKey, accessor);
        return accessor;
    }

    private Accessor cacheLayoutAccessor(Tuple2<Class<?>, String> propertyKey, Accessor accessor) {
        layoutAccessorMap = doCacheAccessor(layoutAccessorMap, propertyKey, accessor);
        return accessor;
    }

    private static Map<Tuple2<Class<?>, String>, Accessor> doCacheAccessor(Map<Tuple2<Class<?>, String>, Accessor> map, Tuple2<Class<?>, String> propertyKey, Accessor accessor) {
        final Option<Method> getter = NodeUtilities.getGetterMethod(propertyKey._1, propertyKey._2);
        if (getter.isDefined()) {
            final Class<?> declaringClass = getter.get().getDeclaringClass();
            final String propertyName = propertyKey._2;
            for (Class<?> clazz = propertyKey._1; !declaringClass.equals(clazz); clazz = clazz.getSuperclass()) {
                map = map.put(Tuple.of(clazz, propertyName), accessor);
            }
            map = map.put(Tuple.of(declaringClass, propertyName), accessor);
        }
        return map;
    }

    private Option<Accessor> createAccessor(Class<?> nodeClass, String propertyName) {
        final Option<Method> getterMethod = NodeUtilities.getGetterMethod(nodeClass, propertyName);
        final Option<MethodHandle> getter = getterMethod.flatMap(NodeUtilities::convertToMethodHandle);

        final Option<Class<?>> type = getterMethod.map(Method::getReturnType);
        if (type.isDefined() && Collection.class.isAssignableFrom(type.get())) {
            if (ObservableList.class.isAssignableFrom(type.get())) {
                final Option<MethodHandle> propertyGetter = NodeUtilities.getPropertyGetter(nodeClass, propertyName);
                return propertyGetter.isDefined() ?
                        propertyGetter.map(methodHandle -> new ListAccessor(methodHandle, dispatcher))
                        : getter.map(ListWithoutListenerAccessor::new);
            }
            return getter.map(CollectionAccessor::new);
        }

        if (NodeUtilities.getSetter(nodeClass, propertyName).isDefined()) {
            return NodeUtilities.getPropertyGetter(nodeClass, propertyName).<Accessor>map(methodHandle -> new PropertyAccessor(methodHandle, dispatcher))
                    .orElse(NodeUtilities.getSetter(nodeClass, propertyName).map(SetterAccessor::new));
        } else {
            return NodeUtilities.getPropertyGetter(nodeClass, propertyName).map(methodHandle -> new ReadOnlyPropertyAccessor(methodHandle, dispatcher));
        }
    }

    private static Option<Accessor> createLayoutAccessor(Class<? extends Parent> parentClass, String propertyName) {
        final Option<MethodHandle> layoutSetter = NodeUtilities.getLayoutSetter(parentClass, propertyName);
        return layoutSetter.isEmpty() ? Option.none() : Option.of(new LayoutConstraintAccessor(layoutSetter.get()));
    }

}
