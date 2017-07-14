package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.geometry.Pos;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("SameParameterValue")
public class VBoxBuilder<B extends VBoxBuilder<B>> extends PaneBuilder<B> {

    private static final String ALIGNMENT = "alignment";
    private static final String SPACING = "spacing";

    public VBoxBuilder(Class<?> nodeClass,
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
        return (B) new VBoxBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B alignment(Pos value) {
        return property(ALIGNMENT, value);
    }

    public B spacing(double value) {
        return property(SPACING, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
