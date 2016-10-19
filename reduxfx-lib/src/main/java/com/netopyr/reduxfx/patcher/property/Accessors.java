package com.netopyr.reduxfx.patcher.property;

import com.netopyr.reduxfx.patcher.NodeBuilder;
import com.netopyr.reduxfx.patcher.Patcher;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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

    public void init(Patcher<ACTION> patcher, NodeBuilder<ACTION> nodeBuilder) {
        registerAccessor(new PropertyKey(ToggleButton.class, "toggleGroup"), new ToggleGroupAccessor<>(getPropertyGetter(ToggleButton.class, "toggleGroup").get(), dispatcher));
        registerAccessor(new PropertyKey(Button.class, "graphic"), new NodeAccessor<>(getPropertyGetter(Button.class, "graphic").get(), dispatcher, nodeBuilder));
        registerAccessor(new PropertyKey(TextField.class, "focused"), new FocusedAccessor<>(getPropertyGetter(TextField.class, "focused").get(), dispatcher));
    }

    public void registerAccessor(PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
        accessorMap = accessorMap.put(propertyKey, accessor);
    }

    public Option<Accessor<?, ACTION>> getAccessor(Node node, String propertyName) {
        final Class<? extends Node> nodeClass = node.getClass();
        final PropertyKey propertyKey = new PropertyKey(nodeClass, propertyName);

        return accessorMap.get(propertyKey)
                .orElse(() -> createAccessor(nodeClass, propertyName).map(accessor -> cacheAccessor(propertyKey, accessor)))
                .orElse(() -> {
                    if (node.getParent() == null) {
                        return Option.none();
                    }
                    final Class<? extends Parent> parentClass = node.getParent().getClass();
                    final PropertyKey layoutKey = new PropertyKey(parentClass, propertyName);
                    return Pane.class.isAssignableFrom(parentClass) ?
                            layoutAccessorMap.get(layoutKey)
                                    .orElse(() -> createLayoutAccessor(parentClass, propertyName).map(accessor -> cacheLayoutAccessor(layoutKey, accessor)))
                            : Option.none();
                });
    }

    private Accessor<?, ACTION> cacheAccessor(PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
        accessorMap = accessorMap.put(propertyKey, accessor);
        return accessor;
    }

    private Accessor<?, ACTION> cacheLayoutAccessor(PropertyKey propertyKey, Accessor<?, ACTION> accessor) {
        layoutAccessorMap = layoutAccessorMap.put(propertyKey, accessor);
        return accessor;
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

    private Option<Method> getGetterMethod(Class<? extends Node> nodeClass, String propertyName) {
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

    private Option<Method> getPropertyGetterMethod(Class<? extends Node> nodeClass, String propertyName) {
        final String propertyGetterName = propertyName + "Property";
        try {
            return Option.of(nodeClass.getMethod(propertyGetterName));
        } catch (NoSuchMethodException | SecurityException e) {
            return Option.none();
        }
    }

    private Option<MethodHandle> getPropertyGetter(Class<? extends Node> nodeClass, String propertyName) {
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

    private Option<MethodHandle> getSetter(Class<? extends Node> nodeClass, String propertyName) {
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

    private Option<MethodHandle> getLayoutSetter(Class<? extends Parent> parentClass, String propertyName) {
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
