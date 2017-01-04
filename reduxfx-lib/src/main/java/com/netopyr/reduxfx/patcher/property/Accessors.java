package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.patcher.NodeBuilder;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

    private Map<PropertyKey, Accessor<?>> accessorMap = HashMap.empty();
    private Map<PropertyKey, Accessor<?>> layoutAccessorMap = HashMap.empty();

    public Accessors(Consumer<Object> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void init(NodeBuilder nodeBuilder) {
        registerAccessor(new PropertyKey(ToggleButton.class, "toggleGroup"), new ToggleGroupAccessor(getPropertyGetter(ToggleButton.class, "toggleGroup").get(), dispatcher));
        registerAccessor(new PropertyKey(Labeled.class, "graphic"), new NodeAccessor(getPropertyGetter(Labeled.class, "graphic").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "top"), new NodeAccessor(getPropertyGetter(BorderPane.class, "top").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "right"), new NodeAccessor(getPropertyGetter(BorderPane.class, "right").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "bottom"), new NodeAccessor(getPropertyGetter(BorderPane.class, "bottom").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "left"), new NodeAccessor(getPropertyGetter(BorderPane.class, "left").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "center"), new NodeAccessor(getPropertyGetter(BorderPane.class, "center").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(Node.class, "focused"), new FocusedAccessor(getPropertyGetter(Node.class, "focused").get(), dispatcher));
    }

    public void registerAccessor(PropertyKey propertyKey, Accessor<?> accessor) {
        cacheAccessor(propertyKey, accessor);
    }

    public Option<Accessor<?>> getAccessor(Node node, String propertyName) {
        final Class<? extends Node> nodeClass = node.getClass();
        final PropertyKey propertyKey = new PropertyKey(nodeClass, propertyName);

        return accessorMap.get(propertyKey)
                .orElse(() -> searchInCache(accessorMap, propertyKey)
                        .orElse(() -> createAccessor(nodeClass, propertyName))
                        .map(accessor -> cacheAccessor(propertyKey, accessor))
                )
                .orElse(() -> {
                    if (node.getParent() == null) {
                        return Option.none();
                    }
                    final Class<? extends Parent> parentClass = node.getParent().getClass();
                    final PropertyKey layoutKey = new PropertyKey(parentClass, propertyName);
                    return Pane.class.isAssignableFrom(parentClass) ?
                            layoutAccessorMap.get(layoutKey)
                                    .orElse(() -> searchInCache(layoutAccessorMap, layoutKey)
                                            .orElse(() -> createLayoutAccessor(parentClass, propertyName))
                                            .map(accessor -> cacheLayoutAccessor(layoutKey, accessor))
                                    )
                            : Option.none();
                });
    }

    private Option<Accessor<?>> searchInCache(Map<PropertyKey, Accessor<?>> map, PropertyKey propertyKey) {
        final String propertyName = propertyKey.getName();
        for (Class<?> clazz = propertyKey.getNodeClass().getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            final Option<Accessor<?>> accessor = map.get(new PropertyKey(clazz, propertyName));
            if (accessor.isDefined()) {
                return accessor;
            }
        }
        return Option.none();
    }

    private Accessor<?> cacheAccessor(PropertyKey propertyKey, Accessor<?> accessor) {
        accessorMap = doCacheAccessor(accessorMap, propertyKey, accessor);
        return accessor;
    }

    private Accessor<?> cacheLayoutAccessor(PropertyKey propertyKey, Accessor<?> accessor) {
        layoutAccessorMap = doCacheAccessor(layoutAccessorMap, propertyKey, accessor);
        return accessor;
    }

    private Map<PropertyKey, Accessor<?>> doCacheAccessor(Map<PropertyKey, Accessor<?>> map, PropertyKey propertyKey, Accessor<?> accessor) {
        final Option<Method> getter = getGetterMethod(propertyKey.getNodeClass(), propertyKey.getName());
        if (getter.isDefined()) {
            final Class<?> declaringClass = getter.get().getDeclaringClass();
            final String propertyName = propertyKey.getName();
            for (Class<?> clazz = propertyKey.getNodeClass(); !declaringClass.equals(clazz); clazz = clazz.getSuperclass()) {
                map = map.put(new PropertyKey(clazz, propertyName), accessor);
            }
            map = map.put(new PropertyKey(declaringClass, propertyName), accessor);
        }
        return map;
    }

    private Option<Accessor<?>> createAccessor(Class<? extends Node> nodeClass, String propertyName) {
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
            return getPropertyGetter(nodeClass, propertyName).<Accessor<?>>map(methodHandle -> new PropertyAccessor<>(methodHandle, dispatcher))
                    .orElse(getSetter(nodeClass, propertyName).map(SetterAccessor::new));
        } else {
            return getPropertyGetter(nodeClass, propertyName).map(methodHandle -> new ReadOnlyPropertyAccessor<>(methodHandle, dispatcher));
        }
    }

    private Option<Accessor<?>> createLayoutAccessor(Class<? extends Parent> parentClass, String propertyName) {
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
