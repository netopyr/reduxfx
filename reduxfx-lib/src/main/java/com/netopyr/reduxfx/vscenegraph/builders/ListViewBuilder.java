package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.impl.patcher.property.ListViewCellFactoryAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.function.Function;

public class ListViewBuilder<BUILDER extends ListViewBuilder<BUILDER, ELEMENT>, ELEMENT> extends ControlBuilder<BUILDER> {

    private static final String ITEMS = "items";
    private static final String CELL_FACTORY = "cellFactory";

    private final Class<ELEMENT> elementClass;

    public ListViewBuilder(Class<?> nodeClass,
                           Class<ELEMENT> elementClass,
                           Map<String, Array<VNode>> childrenMap,
                           Map<String, Option<VNode>> singleChildMap,
                           Map<String, VProperty> properties,
                           Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
        this.elementClass = elementClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new ListViewBuilder<>(getNodeClass(), elementClass, childrenMap, singleChildMap, properties, eventHandlers);
    }


    public BUILDER cellFactory(Function<? super ELEMENT, VNode> value) {
        Accessors.registerAccessor(ListView.class, "cellFactory", ListViewCellFactoryAccessor::new);
        return property(CELL_FACTORY, value);
    }

    public BUILDER items(Seq<? extends ELEMENT> value) {
        return property(ITEMS, value == null? FXCollections.emptyObservableList() : FXCollections.observableList(value.toJavaList()));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("elementClass", elementClass)
                .toString();
    }
}
