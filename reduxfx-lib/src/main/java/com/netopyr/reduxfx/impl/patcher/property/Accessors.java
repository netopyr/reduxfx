package com.netopyr.reduxfx.impl.patcher.property;

import com.netopyr.reduxfx.impl.patcher.NodeUtilities;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Accessors {

    private static final Map<PropertyKey, Accessor> accessorMap = new ConcurrentHashMap<>();
    private static final Map<PropertyKey, Accessor> layoutAccessorMap = new ConcurrentHashMap<>();

    private Accessors() {}

    public static void registerAccessor(Class<?> clazz, String propertyName, Supplier<Accessor> accessor) {
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);
        if (! accessorMap.containsKey(propertyKey)) {
            cacheAccessor(accessorMap, propertyKey, accessor.get());
        }
    }

    public static Option<Accessor> getAccessor(Object node, String propertyName) {
        final Class<?> nodeClass = node.getClass();
        final PropertyKey propertyKey = new PropertyKey(nodeClass, propertyName);

        return Option.of(accessorMap.get(propertyKey))
                .orElse(() -> searchInCache(accessorMap, propertyKey)
                        .orElse(() -> createAccessor(nodeClass, propertyName))
                        .peek(accessor -> cacheAccessor(accessorMap, propertyKey, accessor))
                )
                .orElse(() -> {
                    if (! (node instanceof Node) || ((Node) node).getParent() == null) {
                        return Option.none();
                    }
                    final Class<? extends Parent> parentClass = ((Node) node).getParent().getClass();
                    final PropertyKey layoutKey = new PropertyKey(parentClass, propertyName);
                    return Pane.class.isAssignableFrom(parentClass) ?
                            Option.of(layoutAccessorMap.get(layoutKey))
                                    .orElse(() -> searchInCache(layoutAccessorMap, layoutKey)
                                            .orElse(() -> createLayoutAccessor(parentClass, propertyName))
                                            .peek(accessor -> cacheAccessor(layoutAccessorMap, layoutKey, accessor))
                                    )
                            : Option.none();
                });
    }

    private static Option<Accessor> searchInCache(Map<PropertyKey, Accessor> map, PropertyKey propertyKey) {
        final String propertyName = propertyKey.name;
        for (Class<?> clazz = propertyKey.clazz.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            final Option<Accessor> accessor = Option.of(map.get(new PropertyKey(clazz, propertyName)));
            if (accessor.isDefined()) {
                return accessor;
            }
        }
        return Option.none();
    }

    private static void cacheAccessor(Map<PropertyKey, Accessor> map, PropertyKey propertyKey, Accessor accessor) {
        final Option<Method> getter = NodeUtilities.getGetterMethod(propertyKey.clazz, propertyKey.name);
        if (getter.isDefined()) {
            final Class<?> declaringClass = getter.get().getDeclaringClass();
            final String propertyName = propertyKey.name;
            for (Class<?> clazz = propertyKey.clazz; !declaringClass.equals(clazz); clazz = clazz.getSuperclass()) {
                map.put(new PropertyKey(clazz, propertyName), accessor);
            }
            map.put(new PropertyKey(declaringClass, propertyName), accessor);
        }
    }

    private static Option<Accessor> createAccessor(Class<?> nodeClass, String propertyName) {
        final Option<Method> getterMethod = NodeUtilities.getGetterMethod(nodeClass, propertyName);
        final Option<MethodHandle> getter = getterMethod.flatMap(NodeUtilities::convertToMethodHandle);

        final Option<Class<?>> type = getterMethod.map(Method::getReturnType);
        if (type.isDefined() && Collection.class.isAssignableFrom(type.get())) {
            if (ObservableList.class.isAssignableFrom(type.get())) {
                final Option<MethodHandle> propertyGetter = NodeUtilities.getPropertyGetter(nodeClass, propertyName);
                return propertyGetter.isDefined() ?
                        propertyGetter.map(ListAccessor::new)
                        : getter.map(ListWithoutListenerAccessor::new);
            }
            return getter.map(CollectionAccessor::new);
        }

        if (NodeUtilities.getSetter(nodeClass, propertyName).isDefined()) {
            return NodeUtilities.getPropertyGetter(nodeClass, propertyName).<Accessor>map(PropertyAccessor::new)
                    .orElse(NodeUtilities.getSetter(nodeClass, propertyName).map(SetterAccessor::new));
        } else {
            return NodeUtilities.getPropertyGetter(nodeClass, propertyName).map(ReadOnlyPropertyAccessor::new);
        }
    }

    private static Option<Accessor> createLayoutAccessor(Class<? extends Parent> parentClass, String propertyName) {
        final Option<MethodHandle> layoutSetter = NodeUtilities.getLayoutSetter(parentClass, propertyName);
        return layoutSetter.isEmpty() ? Option.none() : Option.of(new LayoutConstraintAccessor(layoutSetter.get()));
    }

    private static final class PropertyKey {
        private final Class<?> clazz;
        private final String name;

        private PropertyKey(Class<?> clazz, String name) {
            this.clazz = clazz;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            PropertyKey that = (PropertyKey) o;

            return new EqualsBuilder()
                    .append(clazz, that.clazz)
                    .append(name, that.name)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(clazz)
                    .append(name)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("clazz", clazz)
                    .append("name", name)
                    .toString();
        }
    }

}
