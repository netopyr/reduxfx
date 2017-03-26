package com.netopyr.reduxfx.fontawesomefx.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MaterialIconViewBuilder<BUILDER extends MaterialIconViewBuilder<BUILDER>> extends GlyphIconBuilder<BUILDER> {

    public MaterialIconViewBuilder(Class<?> nodeClass,
                                   Array<VNode> children,
                                   Map<String, VProperty> namedChildren,
                                   Map<String, VProperty> properties,
                                   Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BUILDER create(
            Array<VNode> children,
            Map<String, VProperty> namedChildren,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new MaterialIconViewBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER icon(MaterialIcon value) {
        return property(ICON, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
