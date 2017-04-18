package com.netopyr.reduxfx.colorchooser.component;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.VBoxBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.paint.Color;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Builder for the custom component {@link ColorChooserComponent}
 *
 * @param <BUILDER> Parameter required to implement inheritable builders
 */
public class ColorChooserBuilder<BUILDER extends ColorChooserBuilder<BUILDER>> extends VBoxBuilder<BUILDER> {

    ColorChooserBuilder(Class<?> nodeClass,
                        Map<String, Array<VNode>> childrenMap,
                        Map<String, Option<VNode>> singleChildMap,
                        Map<String, VProperty> properties,
                        Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ColorChooserBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    /**
     * With this method, the {@code color} property of this component can be set and a listener can be attached
     *
     * @param value the value that the {@code color} property should be set to
     * @param listener the {@link VChangeListener} that should be called if the value of {@code color} changes
     * @return the new VirtualScenegraph-node of a {@link ColorChooserComponent} with the property {@code color} set
     */
    public BUILDER color(Color value, VChangeListener<? super Color> listener) {
        return property("color", value, listener);
    }

    /**
     * With this method, the {@code color} property of this component can be set and a listener can be attached
     *
     * @param listener the {@link VChangeListener} that should be called if the value of {@code color} changes
     * @return the new VirtualScenegraph-node of a {@link ColorChooserComponent} with the property {@code color} set
     */
    public BUILDER color(VChangeListener<? super Color> listener) {
        return property("color", listener);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
