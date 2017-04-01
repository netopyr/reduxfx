package com.netopyr.reduxfx.fontawesomefx.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.TextBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class GlyphIconBuilder<BUILDER extends GlyphIconBuilder<BUILDER>> extends TextBuilder<BUILDER> {

    private static final String GLYPH_NAME = "glyphName";
    private static final String GLYPH_SIZE = "glyphSize";
    private static final String GLYPH_STYLE = "glyphStyle";
    private static final String SIZE = "size";

    protected static final String ICON = "icon";

    protected GlyphIconBuilder(Class<?> nodeClass,
                            Array<VNode> children,
                            Map<String, VProperty> namedChildren,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, namedChildren, properties, eventHandlers);
    }


    public BUILDER glyphName(String value) {
        return property(GLYPH_NAME, value);
    }

    public BUILDER glyphSize(Number value) {
        return property(GLYPH_SIZE, value);
    }

    public BUILDER glyphStyle(String value) {
        return property(GLYPH_STYLE, value);
    }

    public BUILDER size(String value) {
        return property(SIZE, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
