package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import io.vavr.collection.Array;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings({"unused", "WeakerAccess"})
public class AccordionBuilder<B extends AccordionBuilder<B>> extends ControlBuilder<B> {

    private static final String PANES = "panes";

    public AccordionBuilder(Class<?> nodeClass,
                            Map<String, Array<VNode>> children,
                            Map<String, Option<VNode>> singleChildMap,
                            Map<String, VProperty> properties,
                            Map<VEventType, VEventHandler> eventHandlers) {
        super(nodeClass, children, singleChildMap, properties, eventHandlers);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected B create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (B) new AccordionBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public final B panes(VNode... nodes) {
        return children(PANES, nodes == null? Array.empty() : Array.of(nodes));
    }
    public final B panes(Iterable<VNode> nodes) {
        return children(PANES, nodes == null? Array.empty() : Array.ofAll(nodes));
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .toString();
    }
}
