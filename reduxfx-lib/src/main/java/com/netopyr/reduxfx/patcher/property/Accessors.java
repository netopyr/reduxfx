package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.patcher.NodeBuilder;
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
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
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
        registerAccessor(Tuple.of(ToggleButton.class, "toggleGroup"), new ToggleGroupAccessor(getPropertyGetter(ToggleButton.class, "toggleGroup").get(), dispatcher));
        registerAccessor(Tuple.of(Labeled.class, "graphic"), new NodeAccessor(getPropertyGetter(Labeled.class, "graphic").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "top"), new NodeAccessor(getPropertyGetter(BorderPane.class, "top").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "right"), new NodeAccessor(getPropertyGetter(BorderPane.class, "right").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "bottom"), new NodeAccessor(getPropertyGetter(BorderPane.class, "bottom").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "left"), new NodeAccessor(getPropertyGetter(BorderPane.class, "left").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(BorderPane.class, "center"), new NodeAccessor(getPropertyGetter(BorderPane.class, "center").get(), dispatcher, nodeBuilder));
        registerAccessor(Tuple.of(Node.class, "focused"), new FocusedAccessor(getPropertyGetter(Node.class, "focused").get(), dispatcher));
        registerAccessor(Tuple.of(Stage.class, "scene"), new SceneAccessor(nodeBuilder));
        registerAccessor(Tuple.of(Scene.class, "root"), new NodeAccessor(getPropertyGetter(Scene.class, "root").get(), dispatcher, nodeBuilder));
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

    private Option<Accessor> searchInCache(Map<Tuple2<Class<?>, String>, Accessor> map, Tuple2<Class<?>, String> propertyKey) {
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

    private Map<Tuple2<Class<?>, String>, Accessor> doCacheAccessor(Map<Tuple2<Class<?>, String>, Accessor> map, Tuple2<Class<?>, String> propertyKey, Accessor accessor) {
        final Option<Method> getter = getGetterMethod(propertyKey._1, propertyKey._2);
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
        final Option<Method> getterMethod = getGetterMethod(nodeClass, propertyName);
        final Option<MethodHandle> getter = getterMethod.flatMap(this::convertToMethodHandle);

        final Option<Class<?>> type = getterMethod.map(Method::getReturnType);
        if (type.isDefined() && Collection.class.isAssignableFrom(type.get())) {
            if (ObservableList.class.isAssignableFrom(type.get())) {
                final Option<MethodHandle> propertyGetter = getPropertyGetter(nodeClass, propertyName);
                return propertyGetter.isDefined() ?
                        propertyGetter.map(methodHandle -> new ListAccessor(methodHandle, dispatcher))
                        : getter.map(ListWithoutListenerAccessor::new);
            }
            return getter.map(CollectionAccessor::new);
        }

        if (getSetter(nodeClass, propertyName).isDefined()) {
            return getPropertyGetter(nodeClass, propertyName).<Accessor>map(methodHandle -> new PropertyAccessor(methodHandle, dispatcher))
                    .orElse(getSetter(nodeClass, propertyName).map(SetterAccessor::new));
        } else {
            return getPropertyGetter(nodeClass, propertyName).map(methodHandle -> new ReadOnlyPropertyAccessor(methodHandle, dispatcher));
        }
    }

    private Option<Accessor> createLayoutAccessor(Class<? extends Parent> parentClass, String propertyName) {
        final Option<MethodHandle> layoutSetter = getLayoutSetter(parentClass, propertyName);
        return layoutSetter.isEmpty() ? Option.none() : Option.of(new LayoutConstraintAccessor(layoutSetter.get()));
    }

    private Option<Method> getGetterMethod(Class<?> nodeClass, String propertyName) {
        final String getterNameSuffix = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            return Option.of(nodeClass.getMethod("is" + getterNameSuffix));
        } catch (NoSuchMethodException | SecurityException e) {
            // ignore
        }

        try {
            return Option.of(nodeClass.getMethod("get" + getterNameSuffix));
        } catch (NoSuchMethodException | SecurityException e) {
            return Option.none();
        }
    }

    private Option<Method> getPropertyGetterMethod(Class<?> nodeClass, String propertyName) {
        final String propertyGetterName = propertyName + "Property";
        try {
            return Option.of(nodeClass.getMethod(propertyGetterName));
        } catch (NoSuchMethodException | SecurityException e) {
            return Option.none();
        }
    }

    private Option<MethodHandle> getPropertyGetter(Class<?> nodeClass, String propertyName) {
        final String propertyGetterName = propertyName + "Property";
        try {
            return convertToMethodHandle(nodeClass.getMethod(propertyGetterName));
        } catch (NoSuchMethodException | SecurityException e) {
            return Option.none();
        }
    }

    private Option<MethodHandle> convertToMethodHandle(Method method) {
        try {
            return Option.of(MethodHandles.publicLookup().unreflect(method));
        } catch (IllegalAccessException e) {
            return Option.none();
        }
    }

    private Option<MethodHandle> getSetter(Class<?> nodeClass, String propertyName) {
        final String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            return Array.of(nodeClass.getMethods())
                    .filter(method -> setterName.equals(method.getName()))
                    .filter(method -> method.getParameterCount() == 1)
                    .headOption()
                    .flatMap(this::convertToMethodHandle);
        } catch (Exception ex) {
            return Option.none();
        }
    }

    private Option<MethodHandle> getLayoutSetter(Class<?> parentClass, String propertyName) {
        final String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            return Array.of(parentClass.getMethods())
                    .filter(method -> setterName.equals(method.getName()))
                    .filter(method -> method.getParameterCount() == 2)
                    .filter(method -> Node.class.equals(method.getParameterTypes()[0]))
                    .headOption()
                    .flatMap(this::convertToMethodHandle);
        } catch (Exception ex) {
            return Option.none();
        }
    }
}
