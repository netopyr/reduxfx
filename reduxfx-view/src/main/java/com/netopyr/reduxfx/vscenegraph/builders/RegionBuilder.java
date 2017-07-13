package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"unused", "SameParameterValue"})
public class RegionBuilder<B extends RegionBuilder<B>> extends ParentBuilder<B> {

    private static final String BACKGROUND = "background";
    private static final String MAX_HEIGHT = "maxHeight";
    private static final String MAX_WIDTH = "maxWidth";
    private static final String MIN_HEIGHT = "minHeight";
    private static final String MIN_WIDTH = "minWidth";
    private static final String PREF_HEIGHT = "prefHeight";
    private static final String PREF_WIDTH = "prefWidth";
    private static final String PADDING = "padding";

    public RegionBuilder(Class<?> nodeClass,
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
        return (B) new RegionBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B background(Background value) {
        return property(BACKGROUND, value);
    }
    public B background(Color fill) {
        return property(BACKGROUND, new Background(new BackgroundFill(fill, null, null)));
    }

    public B maxHeight(double value) {
        return property(MAX_HEIGHT, value);
    }

    public B maxWidth(double value) {
        return property(MAX_WIDTH, value);
    }

    public B minHeight(double value) {
        return property(MIN_HEIGHT, value);
    }

    public B minWidth(double value) {
        return property(MIN_WIDTH, value);
    }

    public B prefHeight(double value) {
        return property(PREF_HEIGHT, value);
    }

    public B prefWidth(double value) {
        return property(PREF_WIDTH, value);
    }

    public B padding(double top, double rightLeft, double bottom) {
        return property(PADDING, new Insets(top, rightLeft, bottom, rightLeft));
    }
    public B padding(double topBottom, double rightLeft) {
        return property(PADDING, new Insets(topBottom, rightLeft, topBottom, rightLeft));
    }
    public B padding(double value) {
        return property(PADDING, new Insets(value, value, value, value));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
