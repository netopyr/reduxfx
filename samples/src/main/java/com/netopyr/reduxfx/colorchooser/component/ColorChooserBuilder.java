package com.netopyr.reduxfx.colorchooser.component;

import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.builders.VBoxBuilder;
import com.netopyr.reduxfx.vscenegraph.property.VChangeListener;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColorChooserBuilder<CLASS extends ColorChooserBuilder<CLASS>> extends VBoxBuilder<CLASS> {

    public ColorChooserBuilder(Class<? extends ColorChooserComponent> nodeClass,
                               Array<VProperty<?>> properties,
                               Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ColorChooserBuilder<>((Class<? extends ColorChooserComponent>) nodeClass, properties, eventHandlers);
    }


    /**
     * With this method, the {@code color} property of this component can be set and a listener can be attached
     *
     * @param value the value that the {@code color} property should be set to
     * @param listener the {@link VChangeListener} that should be called if the value of {@code color} changes
     * @return the new VirtualScenegraph-node of a {@link ColorChooserComponent} with the property {@code color} set
     */
    public CLASS color(Color value, VChangeListener<? super Color> listener) {
        return property("color", value, listener);
    }

    /**
     * With this method, the {@code color} property of this component can be set and a listener can be attached
     *
     * @param listener the {@link VChangeListener} that should be called if the value of {@code color} changes
     * @return the new VirtualScenegraph-node of a {@link ColorChooserComponent} with the property {@code color} set
     */
    public CLASS color(VChangeListener<? super Color> listener) {
        return property("color", listener);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
