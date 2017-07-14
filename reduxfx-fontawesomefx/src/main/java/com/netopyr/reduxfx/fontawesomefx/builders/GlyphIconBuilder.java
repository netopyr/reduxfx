package com.netopyr.reduxfx.fontawesomefx.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.TextBuilder;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public abstract class GlyphIconBuilder<B extends GlyphIconBuilder<B>> extends TextBuilder<B> {

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


    public B glyphName(String value) {
        return property(GLYPH_NAME, value);
    }

    public B glyphSize(Number value) {
        return property(GLYPH_SIZE, value);
    }

    public B glyphStyle(String value) {
        return property(GLYPH_STYLE, value);
    }

    public B size(String value) {
        return property(SIZE, value);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
