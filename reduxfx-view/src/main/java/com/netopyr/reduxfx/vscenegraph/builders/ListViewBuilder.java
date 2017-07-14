package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.Accessors;
import com.netopyr.reduxfx.vscenegraph.impl.patcher.property.ListViewCellFactoryAccessor;
import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.function.Function;

public class ListViewBuilder<B extends ListViewBuilder<B, T>, T> extends ControlBuilder<B> {

    private static final String ITEMS = "items";
    private static final String CELL_FACTORY = "cellFactory";

    private final Class<T> elementClass;

    public ListViewBuilder(Class<?> nodeClass,
                           Class<T> elementClass,
                           Map<String, Array<VNode>> childrenMap,
                           Map<String, Option<VNode>> singleChildMap,
                           Map<String, VProperty> properties,
                           Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
        this.elementClass = elementClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new ListViewBuilder<>(getNodeClass(), elementClass, childrenMap, singleChildMap, properties, eventHandlers);
    }


    public B cellFactory(Function<? super T, VNode> value) {
        Accessors.registerAccessor(getNodeClass(), "cellFactory", ListViewCellFactoryAccessor::new);
        return property(CELL_FACTORY, value);
    }

    public B items(Seq<? extends T> value) {
        return property(ITEMS, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("elementClass", elementClass)
                .toString();
    }
}
