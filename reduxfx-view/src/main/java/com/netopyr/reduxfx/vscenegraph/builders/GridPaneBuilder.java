package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("unused")
public class GridPaneBuilder<BUILDER extends GridPaneBuilder<BUILDER>> extends PaneBuilder<BUILDER> {

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
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new GridPaneBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }

    public BUILDER hgap(double value) {
        return property(HGAP, value);
    }

    public BUILDER vgap(double value) {
        return property(VGAP, value);
    }

    public BUILDER columnContraints(ColumnConstraints... values) {
        return property(COLUMN_CONSTRAINTS, values == null? Array.empty() : Array.of(values));
    }
    public BUILDER columnContraints(Iterable<ColumnConstraints> constraints) {
        return property(COLUMN_CONSTRAINTS, constraints == null? Array.empty() : Array.ofAll(constraints));
    }

    public BUILDER rowConstraints(RowConstraints... values) {
        return property(ROW_CONSTRAINTS, values == null? Array.empty() : Array.of(values));
    }
    public BUILDER rowConstraints(Iterable<RowConstraints> constraints) {
        return property(ROW_CONSTRAINTS, constraints == null? Array.empty() : Array.ofAll(constraints));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
