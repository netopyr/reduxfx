package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RegionBuilder<BUILDER extends RegionBuilder<BUILDER>> extends ParentBuilder<BUILDER> {

    private static final String BACKGROUND = "background";
    private static final String MAX_HEIGHT = "maxHeight";
    private static final String MAX_WIDTH = "maxWidth";
    private static final String MIN_HEIGHT = "minHeight";
    private static final String MIN_WIDTH = "minWidth";
    private static final String PREF_HEIGHT = "prefHeight";
    private static final String PREF_WIDTH = "prefWidth";
    private static final String PADDING = "padding";

    public RegionBuilder(Class<?> nodeClass,
                         Array<VNode> children,
                         Map<String, VProperty> namedChildren,
                         Map<String, VProperty> properties,
                         Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new RegionBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER background(Background value) {
        return property(BACKGROUND, value);
    }
    public BUILDER background(Color fill) {
        return property(BACKGROUND, new Background(new BackgroundFill(fill, null, null)));
    }

    public BUILDER maxHeight(double value) {
        return property(MAX_HEIGHT, value);
    }

    public BUILDER maxWidth(double value) {
        return property(MAX_WIDTH, value);
    }

    public BUILDER minHeight(double value) {
        return property(MIN_HEIGHT, value);
    }

    public BUILDER minWidth(double value) {
        return property(MIN_WIDTH, value);
    }

    public BUILDER prefHeight(double value) {
        return property(PREF_HEIGHT, value);
    }

    public BUILDER prefWidth(double value) {
        return property(PREF_WIDTH, value);
    }

    public BUILDER padding(double top, double rightLeft, double bottom) {
        return property(PADDING, new Insets(top, rightLeft, bottom, rightLeft));
    }
    public BUILDER padding(double topBottom, double rightLeft) {
        return property(PADDING, new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }
    public BUILDER padding(double value) {
        return property(PADDING, new Insets(value, value, value, value));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
