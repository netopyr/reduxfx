package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.FocusedAccessor;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.StyleClassAccessor;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.netopyr.reduxfx.vscenegraph.event.VEventType.MOUSE_CLICKED;

@SuppressWarnings({"unused", "SameParameterValue"})
public class NodeBuilder<B extends NodeBuilder<B>> extends Builder<B> {

    private static final String ID = "id";
    private static final String STYLE_CLASS = "styleClass";
    private static final String VISIBLE = "visible";
    private static final String HGROW = "hgrow";
    private static final String VGROW = "vgrow";
    private static final String TOP_ANCHOR = "topAnchor";
    private static final String BOTTOM_ANCHOR = "bottomAnchor";
    private static final String LEFT_ANCHOR = "leftAnchor";
    private static final String RIGHT_ANCHOR = "rightAnchor";
    private static final String ROW_INDEX = "rowIndex";
    private static final String COLUMN_INDEX = "columnIndex";
    private static final String ROW_SPAN = "rowSpan";
    private static final String COLUMN_SPAN = "columnSpan";
    private static final String HOVER = "hover";
    private static final String FOCUSED = "focused";
    private static final String MARGIN = "margin";
    private static final String OPACITY = "opacity";
    private static final String DISABLE = "disable";
    private static final String STYLE = "style";

    public NodeBuilder(Class<?> nodeClass,
                       Map<String, Array<VNode>> childrenMap,
                       Map<String, Option<VNode>> singleChildMap,
                       Map<String, VProperty> properties,
                       Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new NodeBuilder(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }

    protected Object getTypeKey() {
        return getNodeClass();
    }


    public B disable(boolean value) {
        return property(DISABLE, value);
    }

    public B focused(boolean value, VChangeListener<? super Boolean> listener) {
        Accessors.registerAccessor(getNodeClass(), FOCUSED, FocusedAccessor::new);
        return property(FOCUSED, value, listener);
    }

    public B focused(boolean value) {
        Accessors.registerAccessor(getNodeClass(), FOCUSED, FocusedAccessor::new);
        return property(FOCUSED, value);
    }

    public B hover(VChangeListener<? super Boolean> listener) {
        return property(HOVER, listener);
    }

    public B id(String value) {
        return property(ID, value);
    }

    public B opacity(double value) {
        return property(OPACITY, value);
    }

    public B style(String value) {
        return property(STYLE, value);
    }

    public B styleClass(String... value) {
        Accessors.registerAccessor(getNodeClass(), STYLE_CLASS, StyleClassAccessor::new);
        return property(STYLE_CLASS, value == null ? Array.empty() : Array.of(value));
    }

    public B visible(boolean value) {
        return property(VISIBLE, value);
    }


    public B onMouseClicked(VEventHandler<MouseEvent> eventHandler) {
        return onEvent(MOUSE_CLICKED, eventHandler);
    }

    public B hgrow(Priority value) {
        return property(HGROW, value);
    }

    public B vgrow(Priority value) {
        return property(VGROW, value);
    }

    public B bottomAnchor(double value) {
        return property(BOTTOM_ANCHOR, value);
    }

    public B leftAnchor(double value) {
        return property(LEFT_ANCHOR, value);
    }

    public B rightAnchor(double value) {
        return property(RIGHT_ANCHOR, value);
    }

    public B topAnchor(double value) {
        return property(TOP_ANCHOR, value);
    }

    public B margin(double top, double rightLeft, double bottom) {
        return property(MARGIN, new Insets(top, rightLeft, bottom, rightLeft));
    }

    public B margin(double topBottom, double rightLeft) {
        return property(MARGIN, new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }

    public B margin(double value) {
        return property(MARGIN, new Insets(value, value, value, value));
    }

    public B rowIndex(int value) {
        return property(ROW_INDEX, value);
    }

    public B columnIndex(int value) {
        return property(COLUMN_INDEX, value);
    }

    public B columnSpan(int value) {
        return property(COLUMN_SPAN, value);
    }

    public B rowSpan(int value) {
        return property(ROW_SPAN, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
