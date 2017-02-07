package com.netopyr.reduxfx.patcher;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javaslang.control.Option;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

public class NodeUtilities {

    private NodeUtilities() {
    }

    public static Option<Object> getChild(Object obj, int index) {

        return Match(obj).of(

                Case(instanceOf(Parent.class),
                        parent -> parent.getChildrenUnmodifiable().isEmpty() ? Option.none()
                                : Option.of(parent.getChildrenUnmodifiable().get(index))
                ),

                Case(instanceOf(Scene.class),
                        scene -> index == 0? Option.of(scene.getRoot()) : Option.none()
                ),

                Case(instanceOf(Window.class),
                        window -> index == 0? Option.of(window.getScene()) : Option.none()
                ),

                Case($(), Option.none())
        );
    }

    public static Option<Object> getParent(Object obj) {

        return Match(obj).of(

                Case(instanceOf(Node.class),
                        node -> Option.of(node.getParent() != null? node.getParent() : node.getScene())
                ),

                Case(instanceOf(Scene.class),
                        scene -> Option.of(scene.getWindow())
                ),

                Case($(), Option.none())
        );
    }

    public static boolean appendNode(Object parent, Object obj) {

        if (obj instanceof Node) {
            final Node child = (Node) obj;

            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().add(child);
                return true;
            } else if (parent instanceof Group) {
                ((Group) parent).getChildren().add(child);
                return true;
            } else if (parent instanceof Scene) {
                final Scene scene = (Scene) parent;
                if (scene.getRoot() == null && child instanceof Parent) {
                    scene.setRoot((Parent) child);
                    return true;
                }
            }

        } else if (parent instanceof Stage) {
            final Stage stage = (Stage) parent;
            if (stage.getScene() == null && obj instanceof Scene) {
                stage.setScene((Scene) obj);
                return true;
            }
        }

        return false;
    }

    public static boolean replaceNode(Object oldObj, Object newObj) {

        if (oldObj instanceof Node && newObj instanceof Node) {
            final Node oldChild = (Node) oldObj;
            final Node newChild = (Node) newObj;

            final Parent parent = oldChild.getParent();

            if (parent instanceof Pane) {
                final List<Node> children = ((Pane) parent).getChildren();
                final int index = children.indexOf(oldObj);
                children.set(index, newChild);
                return true;
            } else if (parent instanceof Group) {
                final List<Node> children = ((Group) parent).getChildren();
                final int index = children.indexOf(oldObj);
                children.set(index, newChild);
                return true;
            } else {
                final Scene scene = oldChild.getScene();
                if (scene != null && newChild instanceof Parent) {
                    scene.setRoot((Parent) newChild);
                    return true;
                }
            }

        } else if (oldObj instanceof Scene && newObj instanceof Scene) {
            final Stage stage = (Stage) ((Scene) oldObj).getWindow();
            stage.setScene((Scene) newObj);
            return true;
        }

        return false;
    }

    public static boolean removeNode(Object obj) {

        if (obj instanceof Node) {
            final Node node = (Node) obj;
            final Parent parent = node.getParent();
            if (parent instanceof Group) {
                return ((Group) parent).getChildren().remove(node);
            }
            if (parent instanceof Pane) {
                return ((Pane) parent).getChildren().remove(node);
            }
            if (Objects.equals(node.getScene().getRoot(), node)) {
                node.getScene().setRoot(null);
                return true;
            }

        } else if (obj instanceof Scene) {
            final Window window = ((Scene) obj).getWindow();
            if (window instanceof Stage) {
                ((Stage) window).setScene(null);
                return true;
            }
        }

        return false;
    }

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

                Case($(), Collections.emptyMap())
        );

    }
}
