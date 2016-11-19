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

public class Accessors<ACTION> {

    private final Consumer<ACTION> dispatcher;

    private Map<PropertyKey, Accessor<?, ACTION>> accessorMap = HashMap.empty();
    private Map<PropertyKey, Accessor<?, ACTION>> layoutAccessorMap = HashMap.empty();

    public Accessors(Consumer<ACTION> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void init(NodeBuilder<ACTION> nodeBuilder) {
        registerAccessor(new PropertyKey(ToggleButton.class, "toggleGroup"), new ToggleGroupAccessor<>(getPropertyGetter(ToggleButton.class, "toggleGroup").get(), dispatcher));
        registerAccessor(new PropertyKey(Labeled.class, "graphic"), new NodeAccessor<>(getPropertyGetter(Labeled.class, "graphic").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "top"), new NodeAccessor<>(getPropertyGetter(BorderPane.class, "top").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "right"), new NodeAccessor<>(getPropertyGetter(BorderPane.class, "right").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "bottom"), new NodeAccessor<>(getPropertyGetter(BorderPane.class, "bottom").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "left"), new NodeAccessor<>(getPropertyGetter(BorderPane.class, "left").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(BorderPane.class, "center"), new NodeAccessor<>(getPropertyGetter(BorderPane.class, "center").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(Node.class, "focused"), new FocusedAccessor<>(getPropertyGetter(Node.class, "focused").get(), dispatcher));
    }

    public void registerAccessor(PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
        cacheAccessor(propertyKey, accessor);
    }

    public Option<Accessor<?, ACTION>> getAccessor(Node node, String propertyName) {
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

    private Option<Accessor<?, ACTION>> searchInCache(Map<PropertyKey, Accessor<?, ACTION>> map, PropertyKey propertyKey) {
        final String propertyName = propertyKey.getName();
        for (Class<?> clazz = propertyKey.getNodeClass().getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            final Option<Accessor<?, ACTION>> accessor = map.get(new PropertyKey(clazz, propertyName));
            if (accessor.isDefined()) {
                return accessor;
            }
        }
        return Option.none();
    }

    private Accessor<?, ACTION> cacheAccessor(PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
        accessorMap = doCacheAccessor(accessorMap, propertyKey, accessor);
        return accessor;
    }

    private Accessor<?, ACTION> cacheLayoutAccessor(PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
        layoutAccessorMap = doCacheAccessor(layoutAccessorMap, propertyKey, accessor);
        return accessor;
    }

    private Map<PropertyKey, Accessor<?, ACTION>> doCacheAccessor(Map<PropertyKey, Accessor<?, ACTION>> map, PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
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

    private Option<Accessor<?, ACTION>> createAccessor(Class<? extends Node> nodeClass, String propertyName) {
        final Option<Method> getterMethod = getGetterMethod(nodeClass, propertyName);
        final Option<MethodHandle> getter = getterMethod.flatMap(this::convertToMethodHandle);

        final Option<Class<?>> type = getterMethod.map(Method::getReturnType);
        if (type.isDefined() && Collection.class.isAssignableFrom(type.get())) {
            if (ObservableList.class.isAssignableFrom(type.get())) {
                final Option<MethodHandle> propertyGetter = getPropertyGetter(nodeClass, propertyName);
                return propertyGetter.isDefined() ?
                        propertyGetter.map(methodHandle -> new ListAccessor<>(methodHandle, dispatcher))
                        : getter.map(ListWithoutListenerAccessor::new);
            }
            return getter.map(CollectionAccessor::new);
        }

        if (getSetter(nodeClass, propertyName).isDefined()) {
            return getPropertyGetter(nodeClass, propertyName).<Accessor<?, ACTION>>map(methodHandle -> new PropertyAccessor<>(methodHandle, dispatcher))
                    .orElse(getSetter(nodeClass, propertyName).map(SetterAccessor::new));
        } else {
            return getPropertyGetter(nodeClass, propertyName).map(methodHandle -> new ReadOnlyPropertyAccessor<>(methodHandle, dispatcher));
        }
    }

    private Option<Accessor<?, ACTION>> createLayoutAccessor(Class<? extends Parent> parentClass, String propertyName) {
        final Option<MethodHandle> layoutSetter = getLayoutSetter(parentClass, propertyName);
        return layoutSetter.isEmpty() ? Option.none() : Option.of(new LayoutConstraintAccessor<ACTION>(layoutSetter.get()));
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
