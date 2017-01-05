package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RegionBuilder<CLASS extends RegionBuilder<CLASS>> extends ParentBuilder<CLASS> {

    private static final String BACKGROUND = "background";
    private static final String MAX_HEIGHT = "maxHeight";
    private static final String MAX_WIDTH = "maxWidth";
    private static final String MIN_HEIGHT = "minHeight";
    private static final String MIN_WIDTH = "minWidth";
    private static final String PREF_HEIGHT = "prefHeight";
    private static final String PREF_WIDTH = "prefWidth";
    private static final String PADDING = "padding";

    public RegionBuilder(Class<? extends Node> nodeClass,
                         Array<VProperty<?>> properties,
                         Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new RegionBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS background(Background value) {
        return property(BACKGROUND, value);
    }
    public CLASS background(Color fill) {
        return property(BACKGROUND, new Background(new BackgroundFill(fill, null, null)));
    }

    public CLASS maxHeight(double value) {
        return property(MAX_HEIGHT, value);
    }

    public CLASS maxWidth(double value) {
        return property(MAX_WIDTH, value);
    }

    public CLASS minHeight(double value) {
        return property(MIN_HEIGHT, value);
    }

    public CLASS minWidth(double value) {
        return property(MIN_WIDTH, value);
    }

    public CLASS prefHeight(double value) {
        return property(PREF_HEIGHT, value);
    }

    public CLASS prefWidth(double value) {
        return property(PREF_WIDTH, value);
    }

    public CLASS padding(double top, double rightLeft, double bottom) {
        return property(PADDING, new Insets(top, rightLeft, bottom, rightLeft));
    }
    public CLASS padding(double topBottom, double rightLeft) {
        return property(PADDING, new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }
    public CLASS padding(double value) {
        return property(PADDING, new Insets(value, value, value, value));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
