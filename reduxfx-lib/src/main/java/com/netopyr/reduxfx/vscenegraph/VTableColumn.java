package com.netopyr.reduxfx.vscenegraph;

import javaslang.collection.Array;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.function.Function;

public class VTableColumn<ACTION, ITEM> {

    private final Function<ITEM, ?> valueFactory;
    private final Array<VElement<ACTION>> elements;

    public VTableColumn(Function<ITEM, ?> valueFactory, VElement<ACTION>... elements) {
        this.valueFactory = valueFactory;
        this.elements = elements != null? Array.of(elements) : Array.empty();
    }

    public Function<ITEM, ?> getValueFactory() {
        return valueFactory;
    }

    public Array<VElement<ACTION>> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("valueFactory", valueFactory)
                .append("elements", elements)
                .toString();
    }
}
