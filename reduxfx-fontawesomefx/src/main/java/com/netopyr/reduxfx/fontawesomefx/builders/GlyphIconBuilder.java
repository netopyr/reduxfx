package com.netopyr.reduxfx.fontawesomefx.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.TextBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public abstract class GlyphIconBuilder<BUILDER extends GlyphIconBuilder<BUILDER>> extends TextBuilder<BUILDER> {

    private static final String GLYPH_NAME = "glyphName";
    private static final String GLYPH_SIZE = "glyphSize";
    private static final String GLYPH_STYLE = "glyphStyle";
    private static final String SIZE = "size";

    protected static final String ICON = "icon";

    protected GlyphIconBuilder(Class<?> nodeClass,
                               Map<String, Array<VNode>> childrenMap,
                               Map<String, Option<VNode>> singleChildMap,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, childrenMap, singleChildMap, properties, eventHandlers);
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
