package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandlerElement;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javaslang.collection.Array;
import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.function.Function;

public class ListViewBuilder<CLASS extends ListViewBuilder<CLASS>> extends ControlBuilder<CLASS> {

    private static final String DATA = "data";
    private static final String MAPPING = "mapping";

    public ListViewBuilder(Class<? extends Node> nodeClass,
                           Array<VProperty<?>> properties,
                           Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected CLASS create(Class<? extends Node> nodeClass, Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (CLASS) new ListViewBuilder<>(nodeClass, properties, eventHandlers);
    }


    public CLASS cellFactory(Function<Object, VNode> value) {
        return property(MAPPING, value);
    }

    public CLASS items(Seq<?> value) {
        return property(DATA, value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
