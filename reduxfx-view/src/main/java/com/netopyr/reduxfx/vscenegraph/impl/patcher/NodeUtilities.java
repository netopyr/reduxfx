package com.netopyr.reduxfx.vscenegraph.impl.patcher;

import com.netopyr.reduxfx.vscenegraph.javafx.TreeItemWrapper;
import io.vavr.collection.Array;
import io.vavr.control.Option;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.stage.Window;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@SuppressWarnings("WeakerAccess")
public class NodeUtilities {

    private NodeUtilities() {
    }


    public static Object getProperty(Object obj, String name) {
        final Option<MethodHandle> getter = getGetterMethodHandle(obj.getClass(), name);

        if (getter.isDefined()) {
            try {
                return getter.get().invoke(obj);
            } catch (Throwable throwable) {
                throw new IllegalStateException(throwable);
            }
        } else {
            return getProperties(obj).get(name);
        }
    }


    @SuppressWarnings("unchecked")
    public static Map<Object, Object> getProperties(Object obj) {

        return Match(obj).of(

                Case($(instanceOf(Node.class)),
                        Node::getProperties
                ),

                Case($(instanceOf(Scene.class)),
                        Scene::getProperties
                ),

                Case($(instanceOf(Window.class)),
                        Window::getProperties
                ),

                Case($(instanceOf(Dialog.class)),
                        dialog -> (Map<Object, Object>) dialog.getDialogPane().getProperties()
                                .computeIfAbsent("dialog", key -> new HashMap<>())
                ),

                Case($(instanceOf(TreeItemWrapper.class)),
                        TreeItemWrapper::getProperties
                ),

                Case($(instanceOf(Tab.class)),
                    Tab::getProperties
                ),

                Case($(), new HashMap<>())
        );

    }

    public static Option<Method> getGetterMethod(Class<?> nodeClass, String propertyName) {
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

    public static Option<MethodHandle> getGetterMethodHandle(Class<?> nodeClass, String propertyName) {
        return getGetterMethod(nodeClass, propertyName)
                .flatMap(NodeUtilities::convertToMethodHandle);
    }

    public static Option<Method> getPropertyGetterMethod(Class<?> nodeClass, String propertyName) {
        final String propertyGetterName = propertyName + "Property";
        try {
            return Option.of(nodeClass.getMethod(propertyGetterName));
        } catch (NoSuchMethodException | SecurityException e) {
            return Option.none();
        }
    }

    public static Option<MethodHandle> getPropertyGetter(Class<?> nodeClass, String propertyName) {
        return getPropertyGetterMethod(nodeClass, propertyName)
                .flatMap(NodeUtilities::convertToMethodHandle);
    }

    public static Option<MethodHandle> convertToMethodHandle(Method method) {
        try {
            return Option.of(MethodHandles.publicLookup().unreflect(method));
        } catch (IllegalAccessException e) {
            return Option.none();
        }
    }

    public static Option<MethodHandle> getSetter(Class<?> nodeClass, String propertyName) {
        final String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            return Array.of(nodeClass.getMethods())
                    .filter(method -> setterName.equals(method.getName()))
                    .filter(method -> method.getParameterCount() == 1)
                    .headOption()
                    .flatMap(NodeUtilities::convertToMethodHandle)
                    .map(MethodHandle::asFixedArity);
        } catch (Exception ex) {
            return Option.none();
        }
    }

    public static Option<MethodHandle> getLayoutSetter(Class<?> parentClass, String propertyName) {
        final String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            return Array.of(parentClass.getMethods())
                    .filter(method -> setterName.equals(method.getName()))
                    .filter(method -> method.getParameterCount() == 2)
                    .filter(method -> Node.class.equals(method.getParameterTypes()[0]))
                    .headOption()
                    .flatMap(NodeUtilities::convertToMethodHandle);
        } catch (Exception ex) {
            return Option.none();
        }
    }
}
