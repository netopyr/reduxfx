package com.netopyr.reduxfx.vscenegraph.impl.patcher.property;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.NodeUtilities;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class Accessors {

    private static final Lock lock = new ReentrantLock();
    private static final Lock nodeLock = new ReentrantLock();
    private static final Lock nodeListLock = new ReentrantLock();

    private static volatile Map<PropertyKey, Accessor> accessorMap = HashMap.empty();
    private static volatile Map<PropertyKey, Accessor> layoutAccessorMap = HashMap.empty();
    private static volatile Map<PropertyKey, NodeAccessor> nodeAccessorMap = HashMap.empty();
    private static volatile Map<PropertyKey, NodeListAccessor> nodeListAccessorMap = HashMap.empty();

    private Accessors() {
    }

    public static void registerAccessor(Class<?> clazz, String propertyName, Supplier<Accessor> accessor) {
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);
        if (!accessorMap.containsKey(propertyKey)) {
            lock.lock();
            try {
                if (!accessorMap.containsKey(propertyKey)) {
                    final Accessor result = searchInCache(accessorMap, propertyKey).getOrElse(accessor);
                    accessorMap = cacheAccessor(accessorMap, clazz, propertyName, result);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void registerNodeAccessor(Class<?> clazz, String propertyName, Supplier<NodeAccessor> accessor) {
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);
        if (!nodeAccessorMap.containsKey(propertyKey)) {
            nodeLock.lock();
            try {
                if (!nodeAccessorMap.containsKey(propertyKey)) {
                    final NodeAccessor result = searchInCache(nodeAccessorMap, propertyKey).getOrElse(accessor);
                    nodeAccessorMap = cacheAccessor(nodeAccessorMap, clazz, propertyName, result);
                }
            } finally {
                nodeLock.unlock();
            }
        }
    }

    public static void registerNodeListAccessor(Class<?> clazz, String propertyName, Supplier<NodeListAccessor> accessor) {
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);
        if (!nodeListAccessorMap.containsKey(propertyKey)) {
            nodeListLock.lock();
            try {
                if (!nodeListAccessorMap.containsKey(propertyKey)) {
                    final NodeListAccessor result = searchInCache(nodeListAccessorMap, propertyKey).getOrElse(accessor);
                    nodeListAccessorMap = cacheAccessor(nodeListAccessorMap, clazz, propertyName, result);
                }
            } finally {
                nodeListLock.unlock();
            }
        }
    }

    public static Option<Accessor> getAccessor(Object node, String propertyName) {
        final Class<?> clazz = node.getClass();
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);

        final Option<Accessor> result = accessorMap.get(propertyKey);
        if (result.isDefined()) {
            return result;
        }

        lock.lock();
        try {
            return accessorMap.get(propertyKey)
                    .orElse(() ->
                            searchInCache(accessorMap, propertyKey)
                                    .orElse(() -> createAccessor(clazz, propertyName))
                                    .peek(accessor ->
                                            accessorMap = cacheAccessor(accessorMap, clazz, propertyName, accessor)
                                    )
                    )
                    .orElse(() -> {
                        if (node instanceof Node) {
                            final Class<? extends Parent> parentClass = ((Node) node).getParent().getClass();
                            if (Pane.class.isAssignableFrom(parentClass)) {
                                final PropertyKey layoutKey = new PropertyKey(parentClass, propertyName);
                                return layoutAccessorMap.get(layoutKey)
                                        .orElse(() ->
                                                searchInCache(layoutAccessorMap, layoutKey)
                                                        .orElse(() -> createLayoutAccessor(parentClass, propertyName))
                                                        .peek(accessor -> cacheAccessor(layoutAccessorMap, parentClass, propertyName, accessor))
                                        );
                            }
                        }
                        return Option.none();
                    });
        } finally {
            lock.unlock();
        }
    }

    public static Option<NodeAccessor> getNodeAccessor(Object node, String propertyName) {
        final Class<?> clazz = node.getClass();
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);

        final Option<NodeAccessor> result = nodeAccessorMap.get(propertyKey);
        if (result.isDefined()) {
            return result;
        }

        nodeLock.lock();
        try {
            return nodeAccessorMap.get(propertyKey)
                    .orElse(() ->
                            searchInCache(nodeAccessorMap, propertyKey)
                                    .peek(accessor ->
                                            nodeAccessorMap = cacheAccessor(nodeAccessorMap, clazz, propertyName, accessor)
                                    )
                    );
        } finally {
            nodeLock.unlock();
        }
    }

    public static Option<NodeListAccessor> getNodeListAccessor(Object node, String propertyName) {
        final Class<?> clazz = node.getClass();
        final PropertyKey propertyKey = new PropertyKey(clazz, propertyName);

        final Option<NodeListAccessor> result = nodeListAccessorMap.get(propertyKey);
        if (result.isDefined()) {
            return result;
        }

        nodeListLock.lock();
        try {
            return nodeListAccessorMap.get(propertyKey)
                    .orElse(() ->
                            searchInCache(nodeListAccessorMap, propertyKey)
                                    .peek(accessor ->
                                            nodeListAccessorMap = cacheAccessor(nodeListAccessorMap, clazz, propertyName, accessor)
                                    )
                    );
        } finally {
            nodeListLock.unlock();
        }
    }

    private static <T> Option<T> searchInCache(Map<PropertyKey, T> map, PropertyKey propertyKey) {
        final String propertyName = propertyKey.name;
        for (Class<?> clazz = propertyKey.clazz.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            final Option<T> accessor = map.get(new PropertyKey(clazz, propertyName));
            if (accessor.isDefined()) {
                return accessor;
            }
        }
        return Option.none();
    }

    private static <T> Map<PropertyKey, T> cacheAccessor(Map<PropertyKey, T> oldMap, Class<?> nodeClass, String propertyName, T accessor) {
        Map<PropertyKey, T> map = oldMap;
        final Option<Method> getter = NodeUtilities.getGetterMethod(nodeClass, propertyName);
        map = map.put(new PropertyKey(nodeClass, propertyName), accessor);
        if (getter.isDefined()) {
            final Class<?> declaringClass = getter.get().getDeclaringClass();
            if (! nodeClass.equals(declaringClass)) {
                for (Class<?> clazz = nodeClass; !declaringClass.equals(clazz); clazz = clazz.getSuperclass()) {
                    map = map.put(new PropertyKey(clazz, propertyName), accessor);
                }
                map = map.put(new PropertyKey(declaringClass, propertyName), accessor);
            }
        }
        return map;
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
