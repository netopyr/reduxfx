package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VInvalidationListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.MOUSE_CLICKED;

public class NodeBuilder<CLASS extends NodeBuilder<CLASS>> extends VNode {

    private static final String ID = "id";
    private static final String STYLE_CLASS = "styleClass";
    private static final String VISIBLE = "visible";
    private static final String HGROW = "hgrow";
    private static final String TOP_ANCHOR = "topAnchor";
    private static final String BOTTOM_ANCHOR = "bottomAnchor";
    private static final String LEFT_ANCHOR = "leftAnchor";
    private static final String RIGHT_ANCHOR = "rightAnchor";
    private static final String HOVER = "hover";
    private static final String FOCUSED = "focused";

    public NodeBuilder(Class<? extends Node> nodeClass,
                       Array<VProperty<?>> properties,
                       Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new NodeBuilder(nodeClass, properties, eventHandlers);
    }


    public CLASS focused(boolean value, VChangeListener<? super Boolean> listener) {
        return property(FOCUSED, value, listener);
    }
    public CLASS focused(boolean value) {
        return property(FOCUSED, value);
    }

    public CLASS hover(VChangeListener<? super Boolean> listener) {
        return property(HOVER, listener);
    }

    public CLASS id(String value) {
        return property(ID, value);
    }

    public CLASS styleClass(String... value) {
        return property(STYLE_CLASS, value == null? FXCollections.emptyObservableList() : FXCollections.observableArrayList(value));
    }

    public CLASS visible(boolean value) {
        return property(VISIBLE, value);
    }


    public CLASS onMouseClicked(VEventHandler<MouseEvent> eventHandler) {
        return onEvent(MOUSE_CLICKED, eventHandler);
    }


    public CLASS hgrow(Priority value) {
        return property(HGROW, value);
    }

    public CLASS bottomAnchor(double value) {
        return property(BOTTOM_ANCHOR, value);
    }
    public CLASS leftAnchor(double value) {
        return property(LEFT_ANCHOR, value);
    }
    public CLASS rightAnchor(double value) {
        return property(RIGHT_ANCHOR, value);
    }
    public CLASS topAnchor(double value) {
        return property(TOP_ANCHOR, value);
    }



    @SuppressWarnings("unchecked")
    public <T> CLASS property(String name, T value, VChangeListener<? super T> changeListener, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value, changeListener, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public <TYPE> CLASS property(String name, TYPE value, VChangeListener<? super TYPE> changeListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value, changeListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public CLASS property(String name, Object value, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public CLASS property(String name, Object value) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, value)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public CLASS property(String name, VChangeListener<?> changeListener, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, changeListener, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public CLASS property(String name, VChangeListener<?> changeListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, changeListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public CLASS property(String name, VInvalidationListener invalidationListener) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name, invalidationListener)),
                getEventHandlers(),
                this::create
        );
    }
    @SuppressWarnings("unchecked")
    public CLASS property(String name) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties().append(Factory.property(name)),
                getEventHandlers(),
                this::create
        );
    }

    @SuppressWarnings("unchecked")
    public <EVENT extends Event> CLASS onEvent(VEventType type, VEventHandler<EVENT> eventHandler) {
        return (CLASS) Factory.node(
                getNodeClass(),
                getProperties(),
                getEventHandlers().append(Factory.onEvent(type, eventHandler)),
                this:: create
                );
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
