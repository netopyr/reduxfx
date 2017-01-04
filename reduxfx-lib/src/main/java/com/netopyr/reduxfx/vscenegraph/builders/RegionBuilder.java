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

public class RegionBuilder<CLASS extends RegionBuilder<CLASS>> extends NodeBuilder<CLASS> {

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
        return property("background", value);
    }
    public CLASS background(Color fill) {
        return background(new Background(new BackgroundFill(fill, null, null)));
    }

    public CLASS maxHeight(double value) {
        return property("maxHeight", value);
    }

    public CLASS maxWidth(double value) {
        return property("maxWidth", value);
    }

    public CLASS minHeight(double value) {
        return property("minHeight", value);
    }

    public CLASS minWidth(double value) {
        return property("minWidth", value);
    }

    public CLASS prefHeight(double value) {
        return property("prefHeight", value);
    }

    public CLASS prefWidth(double value) {
        return property("prefWidth", value);
    }

    public CLASS padding(double top, double rightLeft, double bottom) {
        return property("padding", new Insets(top, rightLeft, bottom, rightLeft));
    }
    public CLASS padding(double topBottom, double rightLeft) {
        return property("padding", new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }
    public CLASS padding(double value) {
        return property("padding", new Insets(value, value, value, value));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
