package com.netopyr.reduxfx.vscenegraph.builders;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.event.VEventHandler;
import com.netopyr.reduxfx.vscenegraph.event.VEventType;
import com.netopyr.reduxfx.vscenegraph.property.VProperty;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings({"unused", "WeakerAccess"})
public class AccordionBuilder<BUILDER extends AccordionBuilder<BUILDER>> extends ControlBuilder<BUILDER> {

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
    protected BUILDER create(
            Map<String, Array<VNode>> childrenMap,
            Map<String, Option<VNode>> singleChildMap,
            Map<String, VProperty> properties,
            Map<VEventType, VEventHandler> eventHandlers) {
        return (BUILDER) new AccordionBuilder<>(getNodeClass(), childrenMap, singleChildMap, properties, eventHandlers);
    }


    public final BUILDER panes(VNode... nodes) {
        return children(PANES, nodes == null? Array.empty() : Array.of(nodes));
    }
    public final BUILDER panes(Iterable<VNode> nodes) {
        return children(PANES, nodes == null? Array.empty() : Array.ofAll(nodes));
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
