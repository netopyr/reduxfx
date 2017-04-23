package com.netopyr.reduxfx.impl.patcher;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Window;
import javaslang.collection.Array;
import javaslang.control.Option;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

@SuppressWarnings("WeakerAccess")
public class NodeUtilities {

    private NodeUtilities() {
    }


//    public static Object getChild(Object obj, int index) {
//
//        return Match(obj).of(
//
//                Case(instanceOf(Parent.class),
//                        parent -> parent.getChildrenUnmodifiable().get(index)
//                ),
//
//                Case(instanceOf(Stages.class),
//                        stages -> stages.getChildren().get(index)
//                )
//
//        );
//    }


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


//    public static boolean appendNode(Object parent, Object obj) {
//
//        if (obj instanceof Node) {
//            final Node node = (Node) obj;
//
//            return Match(parent).of(
//
//                    Case(instanceOf(Pane.class),
//                            pane -> pane.getChildren().add(node)
//                    ),
//
//                    Case(instanceOf(Group.class),
//                            group -> group.getChildren().add(node)
//                    ),
//
//                    Case($(), false)
//            );
//
//        } else if (obj instanceof Stage && parent instanceof Stages) {
//            return ((Stages) parent).getChildren().add((Stage) obj);
//        }
//
//        return false;
//    }


//    public static boolean replaceNode(Object oldObj, Object newObj) {
//
//        if (oldObj instanceof Node && newObj instanceof Node) {
//            final Node oldChild = (Node) oldObj;
//            final Node newChild = (Node) newObj;
//
//            final Parent parent = oldChild.getParent();
//
//            return Match(parent).of(
//
//                    Case(instanceOf(Pane.class),
//                            pane -> {
//                                final List<Node> children = pane.getChildren();
//                                final int index = children.indexOf(oldChild);
//                                if (index >= 0) {
//                                    children.set(index, newChild);
//                                    return true;
//                                }
//                                return false;
//                            }
//                    ),
//
//                    Case(instanceOf(Group.class),
//                            group -> {
//                                final List<Node> children = group.getChildren();
//                                final int index = children.indexOf(oldChild);
//                                if (index >= 0) {
//                                    children.set(index, newChild);
//                                    return true;
//                                }
//                                return false;
//                            }
//                    ),
//
//                    Case($(), false)
//            );
//
//
//        } else if (oldObj instanceof Stage && newObj instanceof Stage) {
//
//            final Object parent = ((Stage) oldObj).getProperties().get("stages");
//
//            if (parent instanceof Stages) {
//                final List<Stage> children = ((Stages) parent).getChildren();
//                final int index = children.indexOf(oldObj);
//                if (index >= 0) {
//                    children.set(index, (Stage) newObj);
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        return false;
//    }

//    public static boolean removeNode(Object obj) {
//
//        if (obj instanceof Node) {
//            final Node node = (Node) obj;
//            final Parent parent = node.getParent();
//
//            return Match(parent).of(
//
//                    Case(instanceOf(Pane.class),
//                            pane -> pane.getChildren().remove(node)
//                    ),
//
//                    Case(instanceOf(Group.class),
//                            group -> group.getChildren().remove(node)
//                    ),
//
//                    Case($(), false)
//            );
//
//
//        } else if (obj instanceof Stage) {
//            final Object stages = ((Stage) obj).getProperties().get("stages");
//            if (stages instanceof Stages) {
//                return ((Stages) stages).getChildren().remove(obj);
//            }
//        }
//
//        return false;
//    }

    @SuppressWarnings("unchecked")
    public static Map<Object, Object> getProperties(Object obj) {

        return Match(obj).of(

                Case(instanceOf(Node.class),
                        Node::getProperties
                ),

                Case(instanceOf(Scene.class),
                        Scene::getProperties
                ),

                Case(instanceOf(Window.class),
                        Window::getProperties
                ),

                Case(instanceOf(Dialog.class),
                        dialog -> (Map<Object, Object>) dialog.getDialogPane().getProperties()
                                .computeIfAbsent("dialog", key -> new HashMap<>())
                ),

                Case($(), Collections.emptyMap())
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
                    .flatMap(NodeUtilities::convertToMethodHandle);
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
