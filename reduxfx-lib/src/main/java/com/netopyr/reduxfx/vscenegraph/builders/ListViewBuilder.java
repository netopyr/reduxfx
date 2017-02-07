package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.function.Function;

public class ListViewBuilder<BUILDER extends ListViewBuilder<BUILDER, ELEMENT>, ELEMENT> extends ControlBuilder<BUILDER> {

    private static final String DATA = "data";
    private static final String MAPPING = "mapping";

    private final Class<ELEMENT> elementClass;

    public ListViewBuilder(Class<?> nodeClass,
                           Class<ELEMENT> elementClass,
                           Map<String, VProperty> properties,
                           Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, properties, eventHandlers);
        this.elementClass = elementClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(Map<String, VProperty> properties, Map<VEventType, VEventHandler> eventHandlers) {
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
