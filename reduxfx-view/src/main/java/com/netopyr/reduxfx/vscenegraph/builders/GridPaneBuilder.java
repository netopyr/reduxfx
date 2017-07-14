package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("unused")
public class GridPaneBuilder<B extends GridPaneBuilder<B>> extends PaneBuilder<B> {

    private static final String HGAP = "hgap";
    private static final String VGAP = "vgap";
    private static final String COLUMN_CONSTRAINTS = "columnConstraints";
    private static final String ROW_CONSTRAINTS = "rowConstraints";

    public GridPaneBuilder(Class<?> nodeClass,
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
        return (B) new GridPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }

    public B hgap(double value) {
        return property(HGAP, value);
    }

    public B vgap(double value) {
        return property(VGAP, value);
    }

    public B columnContraints(ColumnConstraints... values) {
        return property(COLUMN_CONSTRAINTS, values == null? Array.empty() : Array.of(values));
    }
    public B columnContraints(Iterable<ColumnConstraints> constraints) {
        return property(COLUMN_CONSTRAINTS, constraints == null? Array.empty() : Array.ofAll(constraints));
    }

    public B rowConstraints(RowConstraints... values) {
        return property(ROW_CONSTRAINTS, values == null? Array.empty() : Array.of(values));
    }
    public B rowConstraints(Iterable<RowConstraints> constraints) {
        return property(ROW_CONSTRAINTS, constraints == null? Array.empty() : Array.ofAll(constraints));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
