package com.netopyr.reduxfx.fontawesomefx.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FontAwesomeIconViewBuilder<BUILDER extends FontAwesomeIconViewBuilder<BUILDER>> extends GlyphIconBuilder<BUILDER> {

    public FontAwesomeIconViewBuilder(Class<?> nodeClass,
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
        return (BUILDER) new FontAwesomeIconViewBuilder<>(getNodeClass(), children, namedChildren, properties, eventHandlers);
    }


    public BUILDER icon(FontAwesomeIcon value) {
        return property(ICON, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
