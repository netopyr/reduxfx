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

public class ListViewBuilder<BUILDER extends ListViewBuilder<BUILDER, ELEMENT>, ELEMENT> extends ControlBuilder<BUILDER> {

    private static final String DATA = "data";
    private static final String MAPPING = "mapping";

    private final Class<ELEMENT> elementClass;

    public ListViewBuilder(Class<? extends Node> nodeClass,
                           Class<ELEMENT> elementClass,
                           Array<VProperty<?>> properties,
                           Array<VEventHandlerElement<?>> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
        this.elementClass = elementClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Array<VProperty<?>> properties, Array<VEventHandlerElement<?>> eventHandlers) {
        return (BUILDER) new ListViewBuilder<>(getNodeClass(), elementClass, properties, eventHandlers);
    }


    public BUILDER cellFactory(Function<? super ELEMENT, VNode> value) {
        return property(MAPPING, value);
    }

    public BUILDER items(Seq<? extends ELEMENT> value) {
        return property(DATA, value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("elementClass", elementClass)
                .toString();
    }
}
